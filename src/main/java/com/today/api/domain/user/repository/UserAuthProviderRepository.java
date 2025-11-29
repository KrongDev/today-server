package com.today.api.domain.user.repository;

import com.today.api.domain.user.entity.UserAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthProviderRepository extends JpaRepository<UserAuthProvider, Long> {
    Optional<UserAuthProvider> findByProviderAndProviderId(UserAuthProvider.AuthProvider provider, String providerId);
}
