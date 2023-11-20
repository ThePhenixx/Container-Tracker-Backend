package com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.Controller.ControllerServices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String activeToken;
    private String refreshToken;
    private String registrationNumber;
    private String role;
    private String fullname;
}
