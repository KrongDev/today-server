package com.today.api.domain.user.repository;

import com.today.api.domain.user.model.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);
    // Add other methods as needed by services
}
