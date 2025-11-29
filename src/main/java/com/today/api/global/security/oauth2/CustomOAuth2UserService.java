package com.today.api.global.security.oauth2;

import com.today.api.domain.user.entity.UserEntity;
import com.today.api.domain.user.entity.UserAuthProvider;
import com.today.api.domain.user.repository.UserAuthProviderRepository;
import com.today.api.domain.user.repository.UserJpaRepository;
import com.today.api.global.security.oauth2.user.GoogleOAuth2UserInfo;
import com.today.api.global.security.oauth2.user.KakaoOAuth2UserInfo;
import com.today.api.global.security.oauth2.user.OAuth2UserInfo;
import com.today.api.global.security.oauth2.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserJpaRepository userRepository;
    private final UserAuthProviderRepository userAuthProviderRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the
            // OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        UserAuthProvider.AuthProvider provider = UserAuthProvider.AuthProvider.valueOf(
                oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo oAuth2UserInfo = getOAuth2UserInfo(provider, oAuth2User.getAttributes());

        if (oAuth2UserInfo.getId() == null || oAuth2UserInfo.getId().isEmpty()) {
            throw new OAuth2AuthenticationException("Email not found from OAuth2 provider");
        }

        Optional<UserAuthProvider> userAuthProviderOptional = userAuthProviderRepository
                .findByProviderAndProviderId(provider, oAuth2UserInfo.getId());

        UserEntity user;
        if (userAuthProviderOptional.isPresent()) {
            user = userAuthProviderOptional.get().getUser();
            // Update existing user details if needed
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private OAuth2UserInfo getOAuth2UserInfo(UserAuthProvider.AuthProvider provider,
            java.util.Map<String, Object> attributes) {
        if (provider == UserAuthProvider.AuthProvider.GOOGLE) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (provider == UserAuthProvider.AuthProvider.KAKAO) {
            return new KakaoOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationException("Sorry! Login with " + provider + " is not supported yet.");
        }
    }

    private UserEntity registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        // Use email from OAuth2 info if available, otherwise generate or handle error
        // For now assuming email is available or we use ID as placeholder if email is
        // missing (which is bad but...)
        // OAuth2UserInfo usually has email.
        String email = oAuth2UserInfo.getEmail();
        if (email == null || email.isEmpty()) {
            // Fallback or error. For now let's assume we might need to generate one or
            // fail.
            // Let's use a dummy email if missing for now to avoid crash, or throw.
            // But UserEntity requires email.
            throw new OAuth2AuthenticationException("Email not found from OAuth2 provider");
        }

        UserEntity user = new UserEntity(oAuth2UserInfo.getNickname(), email);
        user = userRepository.save(user);

        UserAuthProvider authProvider = new UserAuthProvider(
                user,
                UserAuthProvider.AuthProvider
                        .valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase()),
                oAuth2UserInfo.getId());
        userAuthProviderRepository.save(authProvider);
        return user;
    }
}
