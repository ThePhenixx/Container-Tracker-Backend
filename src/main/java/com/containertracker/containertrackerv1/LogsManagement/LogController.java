package com.containertracker.containertrackerv1.LogsManagement;

import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.AuthEntity.User;
import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.AuthEntity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/logs/")

public class LogController {

    public final UserRepository userRepository;

    public final LogService logService;

    @GetMapping("logs-by-user/{id}")
    public List<LogEntity> logsByUser(@PathVariable("id") String registrationNumber){
        Optional<User> user0 = userRepository.findByRegistrationNumber(registrationNumber);
        if(user0.isPresent()){
            User user = user0.get();
            return logService.logsByUser(user);
        }
        return null;
    }
}
