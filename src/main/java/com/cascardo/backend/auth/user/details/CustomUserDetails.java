package com.cascardo.backend.auth.user.details;

import com.nightmap.backend.auth.Role;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;



@Builder
public class CustomUserDetails implements UserDetails {

    private Long id;
    private String username;
    private String email;
    private String hashedPassword;
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return roles.stream().
                map(role -> new SimpleGrantedAuthority(role.name())).
                toList();
    }

    @Override
    public String getPassword() {
        return hashedPassword;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }






}
