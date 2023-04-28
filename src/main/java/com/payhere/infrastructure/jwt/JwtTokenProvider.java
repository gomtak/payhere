package com.payhere.infrastructure.jwt;

import com.payhere.application.PrincipalDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtTokenProvider {
    private final long VALID_MILISECOND = 1000L * 60 * 60; // 1 시간
    private final PrincipalDetailsService principalDetailsService;

    @Value("${jwt.secret}")
    private String secretKey;

    public String resolveToken(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }
    private Key getSecretKey(String secretKey) {
        byte[] KeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(KeyBytes);
    }

    private String getUsername(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey(secretKey))
                .build()
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String jwtToken) {
        try {
            log.info("validate..");
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey(secretKey))
                    .build()
                    .parseClaimsJws(jwtToken);
            log.info("{}",claims.getBody().getExpiration());
            return !claims.getBody().getExpiration().before(new Date());
        }catch(Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String jwtToken) {
        UserDetails userDetails = principalDetailsService.loadUserByUsername(getUsername(jwtToken));
        log.info("PASSWORD : {}",userDetails.getPassword());
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }


    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + VALID_MILISECOND))
                .signWith(getSecretKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }
}
