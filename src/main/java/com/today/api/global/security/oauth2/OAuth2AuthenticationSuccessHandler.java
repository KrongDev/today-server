package com.today.api.global.security.oauth2;

import com.today.api.domain.auth.entity.RefreshToken;
import com.today.api.domain.auth.repository.RefreshTokenRepository;
import com.today.api.global.security.jwt.JwtTokenProvider;
import com.today.api.global.security.oauth2.user.UserPrincipal;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${app.oauth2.authorized-redirect-uri}")
    private String redirectUri;

    @Value("${jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenValidityInSeconds;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        if (response.isCommitted()) {
            return;
        }

        String targetUrl = determineTargetUrl(request, response, authentication);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) {
        String accessToken = tokenProvider.createAccessToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        saveRefreshToken((UserPrincipal) authentication.getPrincipal(), refreshToken);

        return UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .build().toUriString();
    }

    private void saveRefreshToken(UserPrincipal userPrincipal, String refreshToken) {
        RefreshToken token = new RefreshToken(
                userPrincipal.getId(),
                refreshToken,
                LocalDateTime.now().plusSeconds(refreshTokenValidityInSeconds));
        refreshTokenRepository.save(token);
    }
}
