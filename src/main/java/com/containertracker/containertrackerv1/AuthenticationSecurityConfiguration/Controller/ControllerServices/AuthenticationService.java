package com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.Controller.ControllerServices;

import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.AuthEntity.User;
import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.AuthEntity.UserRepository;
import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.Email.PasswordRecovery.RecoveryTokenService;
import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.Security.Jwt.JwtGenerator;
import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.Security.Jwt.JwtService;
import com.containertracker.containertrackerv1.LogsManagement.LogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@EnableAsync
@Slf4j       //logs in terminal
public class AuthenticationService {

    private final UserRepository userRepository;

    private final JwtGenerator jwtGenerator;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final RecoveryTokenService recoveryTokenService;

    private final LogService logService;

    @Async
    public void sendRecoveryMail(RegistrationRequest request) throws Exception {
        recoveryTokenService.sendRecoveryMail(request.getEmail());
    }
    public AuthenticationResponse register(RegistrationRequest request) throws Exception {
        Optional<User> user0 = userRepository.findByRegistrationNumber(request.getRegistrationNumber());
        Optional<User> user1 = userRepository.findByEmail(request.getEmail());
        if ( !user0.isPresent() && !user1.isPresent() ){
            User user = User.builder()
                    .registrationNumber(request.getRegistrationNumber())
                    .email(request.getEmail())
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .cin(request.getCin())
                    .phonenumber(request.getPhonenumber())
                    .role(request.getRole())
                    .isAccountNonExpired(true)
                    .isAccountNonLocked(true)
                    .isCredentialsNonExpired(true)
                    .isEnabled(true)
                    .archived(false)
                    .build();

            userRepository.save(user);

            String token = jwtGenerator.generateToken(user);

            sendRecoveryMail(request);

            return AuthenticationResponse.builder()
                    .activeToken(token)
                    .build();
        }
        throw new Exception("User registration failed: User already exists");     //status 403
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getRegistrationNumber(),
                            request.getPassword()
                    )
            );
        }
        catch (Exception e){
            User user = userRepository.findByRegistrationNumber(request.getRegistrationNumber())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
            logService.loggedInFailedUser(user);
            log.error(e.getMessage());
            throw new UsernameNotFoundException("User not found !");
        }
        User user = userRepository.findByRegistrationNumber(request.getRegistrationNumber())
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        String token = jwtGenerator.generateToken(user);
        logService.loggedInUser(user);
        return AuthenticationResponse.builder()
                .activeToken(token)
                .registrationNumber(request.getRegistrationNumber())
                .role(user.getRole().toString())
                .fullname(user.getFirstname()+" "+user.getLastname())
                .build();
    }

    public void logout(User user){
        logService.loggedOutUser(user);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        final String authHeader = request.getHeader("Authorization");
        if( authHeader==null || !authHeader.startsWith("Bearer ")){
            return;
        }
        final String refreshToken = authHeader.substring(7);
        final String registrationNumber;
        try{
            registrationNumber = jwtService.extractRegistrationNumber(refreshToken);
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw new UsernameNotFoundException("User not found !");
        }

        if(!jwtService.isTokenValid(refreshToken)){
            User user = userRepository.findByRegistrationNumber(registrationNumber)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found !"));
            final String newToken = jwtGenerator.generateToken(user);
            AuthenticationResponse.builder()
                    .activeToken(newToken)
                    .refreshToken(refreshToken)
                    .build();
        }

    }
}
