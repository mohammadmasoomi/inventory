package com.github.mohammadmasoomi.inventory.configuration.security.controller;

import com.github.mohammadmasoomi.inventory.configuration.security.service.JwtAuthenticationService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    private final JwtAuthenticationService jwtAuthenticationService;

    public JwtAuthenticationController(JwtAuthenticationService jwtAuthenticationService) {
        this.jwtAuthenticationService = jwtAuthenticationService;
    }

    @PostMapping(value = "/authenticate")
    public String createAuthenticationToken(@RequestParam("username") String username, @RequestParam("password") String password) {
        return jwtAuthenticationService.authenticate(username, password);
    }

}