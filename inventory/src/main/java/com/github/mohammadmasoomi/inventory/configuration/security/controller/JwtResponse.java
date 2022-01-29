package com.github.mohammadmasoomi.inventory.configuration.security.controller;

import java.io.Serial;
import java.io.Serializable;

public record JwtResponse(String jwtToken) implements Serializable {

    @Serial
    private static final long serialVersionUID = -3015771749158365732L;
}
