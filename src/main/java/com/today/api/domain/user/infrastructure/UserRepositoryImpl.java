package com.today.api.domain.user.infrastructure;

import com.today.api.domain.user.entity.UserEntity;
import com.today.api.domain.user.model.User;
import com.today.api.domain.user.repository.UserJpaRepository;
import com.today.api.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        // Convert Domain -> Entity
        UserEntity entity = new UserEntity(user);

        // Save Entity
        UserEntity savedEntity = userJpaRepository.save(entity);

        // Convert Entity -> Domain
        return savedEntity.toDomain();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id).map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(UserEntity::toDomain);
    }
}
