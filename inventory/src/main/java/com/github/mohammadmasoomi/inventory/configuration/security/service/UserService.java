package com.github.mohammadmasoomi.inventory.configuration.security.service;

import com.github.mohammadmasoomi.inventory.configuration.security.jwt.JwtTokenProvider;
import com.github.mohammadmasoomi.inventory.core.entity.security.User;
import com.github.mohammadmasoomi.inventory.core.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
//        try {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        User user = userRepository.findByUsername(username);
        return jwtTokenProvider.createToken(user);
        /*} catch (AuthenticationException e) {
            throw new InventoryJWTException("Invalid username/password supplied", "777",HttpStatus.UNPROCESSABLE_ENTITY);
        }*/
    }
}
