package com.rewinddb.auth.service;

import com.rewinddb.auth.dto.AuthResponse;
import com.rewinddb.auth.dto.LoginRequest;
import com.rewinddb.auth.dto.RegisterRequest;
import com.rewinddb.auth.dto.UserResponse;
import com.rewinddb.auth.entity.AppUser;
import com.rewinddb.auth.mapper.AppUserMapper;
import com.rewinddb.auth.repository.AppUserRepository;
import com.rewinddb.common.enums.UserRole;
import com.rewinddb.common.exception.ConflictException;
import com.rewinddb.common.exception.UnauthorizedException;
import com.rewinddb.security.CurrentUserProvider;
import com.rewinddb.security.JwtService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AppUserRepository appUserRepository;
    private final AppUserMapper appUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CurrentUserProvider currentUserProvider;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String email = normalizeEmail(request.getEmail());
        if (appUserRepository.existsByEmail(email)) {
            throw new ConflictException("Email is already registered");
        }

        Instant now = Instant.now();
        AppUser user = AppUser.builder()
                .email(email)
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .displayName(request.getDisplayName())
                .role(UserRole.USER)
                .enabled(true)
                .createdAt(now)
                .updatedAt(now)
                .build();

        AppUser savedUser = appUserRepository.save(user);
        String token = jwtService.generateToken(savedUser);
        return toAuthResponse(savedUser, token);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(normalizeEmail(request.getEmail()), request.getPassword()));

        AppUser user = appUserRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        String token = jwtService.generateToken(user);
        return toAuthResponse(user, token);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse currentUser() {
        AppUser user = currentUserProvider.getCurrentUser();
        return appUserMapper.toResponse(user);
    }

    private AuthResponse toAuthResponse(AppUser user, String token) {
        return AuthResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .displayName(user.getDisplayName())
                .accessToken(token)
                .tokenType("Bearer")
                .build();
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }
}
