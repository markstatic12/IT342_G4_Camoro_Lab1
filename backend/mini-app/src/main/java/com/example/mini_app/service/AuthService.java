package com.example.mini_app.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.mini_app.dto.AuthResponse;
import com.example.mini_app.dto.LoginRequest;
import com.example.mini_app.dto.MessageResponse;
import com.example.mini_app.dto.RegisterRequest;
import com.example.mini_app.dto.UserDTO;
import com.example.mini_app.dto.UserResponse;
import com.example.mini_app.entity.TokenBlacklist;
import com.example.mini_app.entity.User;
import com.example.mini_app.repository.TokenBlacklistRepository;
import com.example.mini_app.repository.UserRepository;
import com.example.mini_app.security.JwtTokenProvider;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private TokenBlacklistRepository tokenBlacklistRepository;
    
    public MessageResponse register(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        // Create new user
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus("active");
        
        userRepository.save(user);
        
        return new MessageResponse("User registered successfully");
    }
    
    public AuthResponse login(LoginRequest request) {
        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email not found"));
        
        // Validate password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }
        
        // Generate JWT token
        String token = tokenProvider.generateToken(user.getEmail());
        
        // Create user DTO
        UserDTO userDTO = new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getStatus(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
        
        return new AuthResponse(token, userDTO);
    }
    
    public UserResponse getProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        UserDTO userDTO = new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getStatus(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
        
        return new UserResponse(userDTO);
    }

    public MessageResponse logout(String token) {
        if (token == null || token.isBlank()) {
            throw new RuntimeException("Missing token");
        }

        if (!tokenProvider.validateToken(token)) {
            throw new RuntimeException("Invalid token");
        }

        if (tokenBlacklistRepository.existsByToken(token)) {
            return new MessageResponse("Token already invalidated");
        }

        LocalDateTime expiresAt = tokenProvider.getExpiryFromToken(token);
        TokenBlacklist blacklistEntry = new TokenBlacklist(token, expiresAt);
        // Persist immediately so blacklist is visible without waiting for flush/transaction boundaries
        tokenBlacklistRepository.saveAndFlush(blacklistEntry);

        return new MessageResponse("Logged out successfully");
    }
}
