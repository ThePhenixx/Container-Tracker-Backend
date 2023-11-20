package com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.Email.PasswordRecovery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class PasswordChangeRequest {

    private String token;

    private String password;
}
