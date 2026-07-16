package com.rewinddb.auth.service;

import com.rewinddb.auth.dto.AuthResponse;
import com.rewinddb.auth.dto.LoginRequest;
import com.rewinddb.auth.dto.RegisterRequest;
import com.rewinddb.auth.dto.UserResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    UserResponse currentUser();
}
