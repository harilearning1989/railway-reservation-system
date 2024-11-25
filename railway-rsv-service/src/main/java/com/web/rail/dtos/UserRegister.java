package com.web.rail.dtos;

import java.util.List;

public record UserRegister(
        String username,
        String password,
        List<String> roles) {
}
