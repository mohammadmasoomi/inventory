package com.github.mohammadmasoomi.inventory.configuration.security.controller;

import com.github.mohammadmasoomi.inventory.configuration.security.service.JwtAuthenticationService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    private final JwtAuthenticationService jwtAuthenticationService;

    public JwtAuthenticationController(JwtAuthenticationService jwtAuthenticationService) {
        this.jwtAuthenticationService = jwtAuthenticationService;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public String createAuthenticationToken(@RequestParam String username, @RequestParam String password) {
        return jwtAuthenticationService.authenticate(username, password);
    }

}