package com.example.metapor.common.auth;

import com.example.metapor.Domain.user.entity.User;
import com.example.metapor.common.exception.CustomException;
import com.example.metapor.common.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.security.SignatureException;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh.expiration}")
    private long refreshTokenExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateAccessToken(User user) {
        return generateToken(user, accessTokenExpiration, false);
    }

    public String generateRefreshToken(User user) {
        return generateToken(user, refreshTokenExpiration, true);
    }

    private String generateToken(User user, long expiration, boolean isRefresh) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration * 1000);
        return Jwts.builder()
                .header()
                .type(isRefresh ? "refresh" : "access")
                .and()
                .subject(String.valueOf(user.getId()))
                .claim("role", user.isDoctor() ? "ROLE_DOCTOR" : "ROLE_PATIENT")
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    public String getUserIdFromToken(String token) throws CustomException {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token).getPayload();
        String userId = claims.getSubject();
        if (!StringUtils.hasText(userId)) {
            throw CustomException.of(ErrorCode.TOKEN_NOT_VALID);
        }
        return userId;
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token).getPayload();
    }

    public void validateToken(String token) {
        Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);

    }

    public ErrorCode verifyToken(String token) {
        if (!StringUtils.hasText(token)) {
            return ErrorCode.TOKEN_NOT_FOUND;
        }
        try {
            validateToken(token);
            return ErrorCode.AUTH_SUCCESS;
        } catch (ExpiredJwtException e) {
            return ErrorCode.TOKEN_EXPIRED;
        } catch (UnsupportedJwtException e) {
            return ErrorCode.TOKEN_NOT_SUPPORTED;
        } catch (IllegalStateException | MalformedJwtException e) {
            return ErrorCode.TOKEN_NOT_VALID;
        }
    }

}