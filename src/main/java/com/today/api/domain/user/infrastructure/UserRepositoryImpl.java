package com.today.api.domain.user.infrastructure;

import com.today.api.domain.user.domain.model.User;
import com.today.api.domain.user.domain.repository.UserDomainRepository;
import com.today.api.domain.user.domain.repository.UserJpaRepository;
import com.today.api.domain.user.infrastructure.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserDomainRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        UserEntity entity = new UserEntity(user);
        return userJpaRepository.save(entity).toDomain();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id)
                .map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(UserEntity::toDomain);
    }
}
