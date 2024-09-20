package com.project.ets.security;

import com.project.ets.entity.User;
import com.project.ets.privileges.Privilege;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class CustomUserDatails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRole().getPrivileges().stream().map(privilage->new SimpleGrantedAuthority(privilage.name())).toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }
}
