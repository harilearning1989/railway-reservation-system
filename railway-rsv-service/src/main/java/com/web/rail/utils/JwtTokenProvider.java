package com.web.rail.utils;

import com.web.rail.enums.RoleType;
import com.web.rail.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.token.validity}")
    private long expirationTimeInMinutes;
    @Value("${jwt.token.issuer}")
    private String tokenIssuer;

    // Generate JWT token with roles
    public String generateTokenTmp(String username, Set<RoleType> roles) {
        String roleClaims = roles.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roleClaims) // Storing roles as a claim
                .setIssuedAt(new Date())
                .setIssuer(tokenIssuer)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeInMinutes))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Generate JWT token with roles
    public String generateToken(String username, Set<String> roles) {
        /*String roleClaims = roles.stream()
                .collect(Collectors.joining(","));*/

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles) // Storing roles as a claim
                .setIssuedAt(new Date())
                .setIssuer(tokenIssuer)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeInMinutes))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Get username from JWT token
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // Get roles from JWT token
    public Set<UserRole> getRolesFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String rolesString = claims.get("roles", String.class);
        return rolesString == null ? Set.of() :
                Set.of(rolesString.split(",")).stream()
                        .map(UserRole::valueOf)
                        .collect(Collectors.toSet());
    }

    // Validate JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer(tokenIssuer)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeInMinutes))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRolesTmp(String token) {
        return extractAllClaims(token).get("roles", String.class);
    }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("roles", List.class); // Adjust key as per your JWT structure
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}

