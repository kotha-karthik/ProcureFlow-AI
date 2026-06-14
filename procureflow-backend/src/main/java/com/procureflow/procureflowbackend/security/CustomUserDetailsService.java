package com.procureflow.procureflowbackend.security;

import com.procureflow.procureflowbackend.auth.entity.User;
import com.procureflow.procureflowbackend.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        userRepository.findRoleCodesByEmail(email)
                .forEach(role ->
                        authorities.add(
                                new SimpleGrantedAuthority("ROLE_" + role)));

        userRepository.findPermissionCodesByEmail(email)
                .forEach(permission ->
                        authorities.add(
                                new SimpleGrantedAuthority(permission)));

        return new CustomUserDetails(
                user.getUserId(),
                user.getOrganizationId(),
                user.getEmail(),
                user.getPasswordHash(),
                authorities
        );
    }
}
