package com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.Email.PasswordRecovery;

import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.AuthEntity.User;
import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.AuthEntity.UserRepository;
import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.Email.EmailDetails;
import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.Email.EmailService;
import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.Security.Jwt.JwtService;
import com.containertracker.containertrackerv1.LogsManagement.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j

public class RecoveryTokenService {

    private final EmailService emailService;

    private final UserRepository userRepository;

    private final RecoveryTokenGenerator recoveryTokenGenerator;

    private final JwtService jwtService;

    private final BCryptPasswordEncoder passwordEncoder;

    private final LogService logService;

    public PasswordRecoveryResponse sendRecoveryMail(String email) throws Exception{

        Optional<User> opUser = userRepository.findByEmail(email);

        if (opUser.isPresent()) {
            User user = opUser.get();
            String token = recoveryTokenGenerator.generateToken(user);   //token can be coded using secret key.
            String link = "http://localhost:5173/change-password/" + token;
            EmailDetails mail = EmailDetails.builder()
                    .recipient(user.getEmail())
                    .subject("Password recovery.")
                    .msgBody("A request to change you ContainerTracker account password has been made.\n\n Recovery link: " + link)
                    .build();
            emailService.sendSimpleMail(mail);
            logService.passwordRecoveryDemand(user);
            return new PasswordRecoveryResponse(200, "Email sent successfully.");
        }
        if (!opUser.isPresent()) {
            return new PasswordRecoveryResponse(500, "No user with such an email.");
        }
        return null;
    }

    public PasswordRecoveryResponse changeAccountPassword(PasswordChangeRequest request) throws Exception{
        String password = request.getPassword();
        String token = request.getToken();
        String registrationNumber = jwtService.extractRegistrationNumber(token);
        try{
            if (!jwtService.isTokenExpired(token)){
                log.error("token is valid !");
                Optional<User> opUser = userRepository.findByRegistrationNumber(registrationNumber);
                if (opUser.isPresent()){
                    User user = opUser.get();
                    user.setPassword(passwordEncoder.encode(password));
                    userRepository.save(user);
                    logService.passwordChange(user);
                }
                return new PasswordRecoveryResponse(200, "Password changed successfully.");
            }
        }
        catch (Exception e) {                   // MUST LEARN TO HANDLE EXCEPTIONS
            return new PasswordRecoveryResponse(500, "Recovery token has been expired.");
        }
        return null;
    }
}
