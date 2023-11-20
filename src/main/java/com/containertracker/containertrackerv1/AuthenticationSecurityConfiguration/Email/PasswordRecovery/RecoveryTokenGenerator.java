package com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.Email.PasswordRecovery;

import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.AuthEntity.User;
import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.Security.Jwt.JwtConfiguration;
import io.jsonwebtoken.Jwts;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Data
@Builder
@RequiredArgsConstructor
@Component

public class RecoveryTokenGenerator {

    private final JwtConfiguration jwtConfiguration;

    private final RecoveryTokenConfiguration recoveryTokenConfiguration;

    public String generateToken(User user){
        return Jwts
                .builder()
                .setSubject(user.getRegistrationNumber())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(recoveryTokenConfiguration.getExpirationTime())))
                .signWith(jwtConfiguration.getHashedSecretKey())
                .compact();

    }
}
