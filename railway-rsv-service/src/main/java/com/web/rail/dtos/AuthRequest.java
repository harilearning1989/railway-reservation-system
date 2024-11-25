package com.web.rail.dtos;

public record AuthRequest(
        String username,
        String password
) {
}
