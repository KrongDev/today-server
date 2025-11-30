package com.today.api.global.security.oauth2;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthProviderRepository extends JpaRepository<UserAuthProvider, Long> {
    Optional<UserAuthProvider> findByProviderAndProviderId(UserAuthProvider.AuthProvider provider, String providerId);
}
