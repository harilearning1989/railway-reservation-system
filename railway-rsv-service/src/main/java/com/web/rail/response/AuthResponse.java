package com.web.rail.response;

public record AuthResponse(
        String token,
        String username,
        Integer statusCode
) {
}
