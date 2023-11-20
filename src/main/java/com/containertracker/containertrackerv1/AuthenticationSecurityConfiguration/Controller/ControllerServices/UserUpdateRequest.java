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
public class UserUpdateRequest {

    private String registrationNumber;
    private String email;
    private String phonenumber;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Role role;
}
