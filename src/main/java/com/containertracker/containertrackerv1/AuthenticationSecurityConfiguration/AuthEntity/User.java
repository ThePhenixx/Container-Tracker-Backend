package com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.AuthEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Builder
@Entity(name = "userAccount")
@AllArgsConstructor
@NoArgsConstructor

public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uid;
    private String firstname;
    private String lastname;
    private String email;
    private String registrationNumber;
    private String password;
    private String cin;
    private String phonenumber;
    private Role role;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    private boolean archived;

    public String toLogString(){
        String text = "Registration number: "+registrationNumber+".\n"
                        +"Fullname: "+firstname+" "+lastname+".\n"
                        +"Email: "+email+".\n"
                        +"CIN: "+cin+". Role: "+role+". Phone number: "+phonenumber+".\n"
                        +"isAccountNonExpired: "+isCredentialsNonExpired+". isAccountNonLocked: "+isAccountNonLocked+".\n"
                        +"isCredentialsNonExpired: "+isCredentialsNonExpired+". isEnabled: "+isEnabled+". Archived: "+archived+".\n";
        return text;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return registrationNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
