package com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.Controller;

import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.AuthEntity.User;
import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.AuthEntity.UserRepository;
import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.Controller.ControllerServices.AuthenticationRequest;
import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.Controller.ControllerServices.AuthenticationResponse;
import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.Controller.ControllerServices.AuthenticationService;
import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.Controller.ControllerServices.RegistrationRequest;
import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.Security.Jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor

public class AuthenticationController {

    private final AuthenticationService authService;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistrationRequest request) throws Exception {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest httpRequest){
        String authHeader = httpRequest.getHeader("Authorization");
        final String token = authHeader.substring(7);
        String registrationNumber = jwtService.extractRegistrationNumber(token);
        Optional<User> user0 = userRepository.findByRegistrationNumber(registrationNumber);
        if(user0.isPresent()){
            User user = user0.get();
            authService.logout(user);
        }
    }

    @GetMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response){
        authService.refreshToken(request, response);
    }
}
