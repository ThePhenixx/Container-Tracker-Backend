package com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.Security.Jwt;

import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.AuthEntity.User;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class JwtGenerator {

    private final JwtConfiguration jwtConfiguration;

    public String generateToken(User user){
        return Jwts
                .builder()
                .setSubject(user.getRegistrationNumber())
                .claim("uid", user.getUid())
                .claim("firstname", user.getFirstname())
                .claim("lastname", user.getLastname())
                .claim("email", user.getEmail())
                .claim("phonenumber", user.getPhonenumber())
                .claim("role", user.getRole())
                .claim("accountNonExpired", user.isAccountNonExpired())
                .claim("accountNonLocked", user.isAccountNonLocked())
                .claim("credentialsNonExpired", user.isCredentialsNonExpired())
                .claim("enable", user.isEnabled())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(jwtConfiguration.getJwtExpirationTime())))
                .signWith(jwtConfiguration.getHashedSecretKey())
                .compact();

    }
}
