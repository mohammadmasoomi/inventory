package com.github.mohammadmasoomi.inventory.configuration.security.controller;

import com.github.mohammadmasoomi.inventory.configuration.security.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    private final UserService userService;

    public JwtAuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public String createAuthenticationToken(@RequestParam String username, @RequestParam String password) {
        return userService.authenticate(username, password);
    }

}