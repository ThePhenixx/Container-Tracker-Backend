package com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.Controller;


import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.AuthEntity.User;
import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.AuthEntity.UserRepository;
import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.Controller.ControllerServices.UserUpdateRequest;
import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.Security.Jwt.JwtService;
import com.containertracker.containertrackerv1.LogsManagement.LogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users-controller")

public class UsersController {

    private final UserRepository userRepository;

    private final LogService logService;

    private final JwtService jwtService;

    @GetMapping("/users/starting-with/{user}")
    public List<User> findUsers(@PathVariable("user") String user, HttpServletRequest httpRequest) {
        String authHeader = httpRequest.getHeader("Authorization");
        final String token = authHeader.substring(7);
        String registrationNumber0 = jwtService.extractRegistrationNumber(token);
        Optional<User> admin01 = userRepository.findByRegistrationNumber(registrationNumber0);
        if(admin01.isPresent()){
            User admin = admin01.get();
            logService.searchUser(admin, user);
            return userRepository.findUsers(user);
        }
        return null;
    }

    @GetMapping("/user/{registrationNumber}")
    public User findUser(@PathVariable("registrationNumber") String registrationNumber, HttpServletRequest httpRequest) {
        User user = null;
        Optional<User> user0 = userRepository.findByRegistrationNumber(registrationNumber);
        if (user0.isPresent()) {
            user=user0.get();
        }
        String authHeader = httpRequest.getHeader("Authorization");
        final String token = authHeader.substring(7);
        String registrationNumber0 = jwtService.extractRegistrationNumber(token);
        Optional<User> admin01 = userRepository.findByRegistrationNumber(registrationNumber0);
        if(admin01.isPresent()){
            User admin = admin01.get();
            logService.accessUser(admin, user);
            return user;
        }
        return null;
    }

    @GetMapping("/users")
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/all-archived-users")
    public List<User> findAllArchivedUsers() {
        return userRepository.findAllArchivedUsers();
    }

    @GetMapping("/all-retrieved-users")
    public List<User> findAllRetrievedUsers() {
        return userRepository.findAllRetrievedUsers();
    }

    @GetMapping("/archived-users/{id}")
    public List<User> findArchivedUsers(@PathVariable("id") String id, HttpServletRequest httpRequest) {
        String authHeader = httpRequest.getHeader("Authorization");
        final String token = authHeader.substring(7);
        String registrationNumber0 = jwtService.extractRegistrationNumber(token);
        Optional<User> admin01 = userRepository.findByRegistrationNumber(registrationNumber0);
        if(admin01.isPresent()){
            User admin = admin01.get();
            logService.searchArchivedUser(admin, id);
            return userRepository.findArchivedUsers(id);
        }
        return null;
    }

    @GetMapping("/retrieved-users/{id}")
    public List<User> findRetrievedUsers(@PathVariable("id") String id, HttpServletRequest httpRequest) {
        String authHeader = httpRequest.getHeader("Authorization");
        final String token = authHeader.substring(7);
        String registrationNumber0 = jwtService.extractRegistrationNumber(token);
        Optional<User> admin01 = userRepository.findByRegistrationNumber(registrationNumber0);
        if(admin01.isPresent()){
            User admin = admin01.get();
            logService.searchUser(admin, id);
            return userRepository.findRetrievedUsers(id);
        }
        return null;
    }

    @GetMapping("/archive-user/{registrationNumber}")
    public void archiveUser(@PathVariable("registrationNumber") String registrationNumber, HttpServletRequest httpRequest) {
        String authHeader = httpRequest.getHeader("Authorization");
        final String token = authHeader.substring(7);
        String registrationNumber0 = jwtService.extractRegistrationNumber(token);
        Optional<User> admin01 = userRepository.findByRegistrationNumber(registrationNumber0);
        if(admin01.isPresent()){
            User admin = admin01.get();
            Optional<User> user0 = userRepository.findByRegistrationNumber(registrationNumber);
            if (user0.isPresent()) {
                User user = user0.get();
                logService.archiveUserLog(admin, user);
                user.setEnabled(false);
                user.setArchived(true);
                userRepository.save(user);
            }
        }
    }

    @GetMapping("/retrieve-user/{registrationNumber}")
    public void retrieveUser(@PathVariable("registrationNumber") String registrationNumber, HttpServletRequest httpRequest) {
        String authHeader = httpRequest.getHeader("Authorization");
        final String token = authHeader.substring(7);
        String registrationNumber0 = jwtService.extractRegistrationNumber(token);
        Optional<User> admin01 = userRepository.findByRegistrationNumber(registrationNumber0);
        if(admin01.isPresent()){
            User admin = admin01.get();
            Optional<User> user0 = userRepository.findByRegistrationNumber(registrationNumber);
            if (user0.isPresent()) {
                User user = user0.get();
                logService.retrieveUserLog(admin, user);
                user.setEnabled(true);
                user.setArchived(false);
                userRepository.save(user);
            }
        }
    }


    @PutMapping ("/update-user")
    public void updateUser(@RequestBody UserUpdateRequest request, HttpServletRequest httpRequest) {
        String authHeader = httpRequest.getHeader("Authorization");
        final String token = authHeader.substring(7);
        String registrationNumber0 = jwtService.extractRegistrationNumber(token);
        Optional<User> admin01 = userRepository.findByRegistrationNumber(registrationNumber0);
        if(admin01.isPresent()){
            User admin = admin01.get();
            Optional<User> user0 = userRepository.findByRegistrationNumber(request.getRegistrationNumber());
            if (user0.isPresent()) {
                User user = user0.get();
                logService.UpdateUserLog(admin, user);
                user.setEmail(request.getEmail());
                user.setPhonenumber(request.getPhonenumber());
                user.setAccountNonExpired(request.isAccountNonExpired());
                user.setAccountNonLocked(request.isAccountNonLocked());
                user.setCredentialsNonExpired(request.isCredentialsNonExpired());
                user.setEnabled(request.isEnabled());
                user.setRole(request.getRole());
                userRepository.save(user);
            }
        }
    }
}
