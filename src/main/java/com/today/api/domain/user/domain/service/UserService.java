package com.today.api.domain.user.domain.service;

import com.today.api.domain.user.domain.model.User;
import com.today.api.domain.user.domain.repository.UserDomainRepository;
import com.today.api.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserDomainRepository userDomainRepository;

    public User getUserProfile(Long userId) {
        return userDomainRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    public User getUserByEmail(String email) {
        return userDomainRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    @Transactional
    public User updateNickname(Long userId, String newNickname) {
        User user = getUserProfile(userId);
        user.changeNickname(newNickname);
        return userDomainRepository.save(user);
    }

    @Transactional
    public User updateNotificationSetting(Long userId, boolean enabled) {
        User user = getUserProfile(userId);
        user.changeNotificationSetting(enabled);
        return userDomainRepository.save(user);
    }

    @Transactional
    public void deactivateUser(Long userId) {
        User user = getUserProfile(userId);
        user.deactivate();
        userDomainRepository.save(user);
    }
}
