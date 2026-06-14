package com.procureflow.procureflowbackend.auth.service;

import com.procureflow.procureflowbackend.auth.entity.User;
import com.procureflow.procureflowbackend.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmailAndIsDeletedFalse(email);
    }
}
