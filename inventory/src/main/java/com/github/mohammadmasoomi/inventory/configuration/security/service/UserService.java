package com.github.mohammadmasoomi.inventory.configuration.security.service;

import com.github.mohammadmasoomi.inventory.configuration.security.jwt.JwtTokenProvider;
import com.github.mohammadmasoomi.inventory.core.entity.security.User;
import com.github.mohammadmasoomi.inventory.core.repository.UserRepository;
import com.github.mohammadmasoomi.inventory.exception.AppErrorMessage;
import com.github.mohammadmasoomi.inventory.exception.InventoryJWTException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
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
            AppErrorMessage securityErrorMessage = AppErrorMessage.UNKNOWN_AUTHENTICATION_ERROR;
            if (authException instanceof BadCredentialsException) {
                securityErrorMessage = AppErrorMessage.WRONG_CREDENTIALS;
            } else if (authException instanceof LockedException) {
                securityErrorMessage = AppErrorMessage.TEMPORARY_ACCOUNT_LOCKED;
            } else if (authException instanceof CredentialsExpiredException) {
                securityErrorMessage = AppErrorMessage.CREDENTIALS_EXPIRED;
            } else if (authException instanceof DisabledException) {
                securityErrorMessage = AppErrorMessage.USER_ALREADY_IS_DISABLED;
            } else if (authException instanceof AccountExpiredException) {
                securityErrorMessage = AppErrorMessage.ACCOUNT_EXPIRED;
            } else if (authException instanceof InsufficientAuthenticationException) {
                securityErrorMessage = AppErrorMessage.INSUFFICIENT_AUTHENTICATION;
            }
            throw new InventoryJWTException(securityErrorMessage);
        }
    }
}
