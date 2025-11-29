package com.today.api.domain.auth.service;

import com.today.api.domain.auth.dto.LogoutRequest;
import com.today.api.domain.auth.dto.TokenRefreshRequest;
import com.today.api.domain.auth.dto.TokenResponse;
import com.today.api.domain.auth.entity.RefreshToken;
import com.today.api.domain.auth.repository.RefreshTokenRepository;
import com.today.api.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenValidityInSeconds;

    @Transactional
    public TokenResponse refreshToken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        // 1. Validate Token Integrity
        if (!tokenProvider.validateToken(requestRefreshToken)) {
            throw new BadCredentialsException("Invalid Refresh Token");
        }

        // 2. Check existence in DB
        RefreshToken storedToken = refreshTokenRepository.findByToken(requestRefreshToken)
                .orElseThrow(() -> new BadCredentialsException("Refresh Token not found in database"));

        // 3. Get User Authentication
        Authentication authentication = tokenProvider.getAuthentication(requestRefreshToken);

        // 4. Rotate Tokens
        String newAccessToken = tokenProvider.createAccessToken(authentication);
        String newRefreshToken = tokenProvider.createRefreshToken(authentication);

        // 5. Invalidate Old & Save New
        refreshTokenRepository.delete(storedToken);

        RefreshToken newToken = new RefreshToken(
                storedToken.getUserId(),
                newRefreshToken,
                LocalDateTime.now().plusSeconds(refreshTokenValidityInSeconds));
        refreshTokenRepository.save(newToken);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }

    @Transactional
    public void logout(LogoutRequest request) {
        String refreshToken = request.getRefreshToken();
        if (tokenProvider.validateToken(refreshToken)) {
            refreshTokenRepository.deleteByToken(refreshToken);
        }
    }
}
