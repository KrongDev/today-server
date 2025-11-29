package com.today.api.domain.user.domain.repository;

import com.today.api.domain.user.domain.model.User;

import java.util.Optional;

public interface UserDomainRepository {
    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);
}
