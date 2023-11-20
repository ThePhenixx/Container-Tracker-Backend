package com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.Controller.ControllerServices;

import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.AuthEntity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String registrationNumber;
    private String cin;
    private String phonenumber;
    private Role role;
}
