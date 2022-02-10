package com.github.mohammadmasoomi.inventory.configuration.security.service;

import com.github.mohammadmasoomi.inventory.configuration.security.InventoryBasicAuthenticationEntryPoint;
import com.github.mohammadmasoomi.inventory.configuration.security.jwt.JwtTokenProvider;
import com.github.mohammadmasoomi.inventory.core.entity.security.User;
import com.github.mohammadmasoomi.inventory.core.repository.security.UserRepository;
import com.github.mohammadmasoomi.inventory.exception.AppErrorMessage;
import com.github.mohammadmasoomi.inventory.exception.InventoryJWTException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class JwtAuthenticationService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    public String authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = userRepository.findByUsername(username);
            return jwtTokenProvider.createToken(user);
        } catch (AuthenticationException authException) {
            AppErrorMessage securityErrorMessage = InventoryBasicAuthenticationEntryPoint.getAuthenticationErrorMessage(authException);
            throw new InventoryJWTException(securityErrorMessage);
        }
    }
}
