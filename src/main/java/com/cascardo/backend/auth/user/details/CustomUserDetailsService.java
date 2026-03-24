package com.cascardo.backend.auth.user.details;

import com.nightmap.backend.auth.dto.CustomUserDetailsDto;
import com.nightmap.backend.user.User;
import com.nightmap.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado."));

        return CustomUserDetails.builder().
                id(user.getId()).
                email(user.getEmail()).
                username(user.getUsername()).
                hashedPassword(user.getHashedPassword()).
                roles(user.getRoles()).
                build();

    }

    public CustomUserDetails loadUser(CustomUserDetailsDto userDetailsDto) {

        return CustomUserDetails.builder().
                id(userDetailsDto.id()).
                email(userDetailsDto.email()).
                username(userDetailsDto.username()).
                hashedPassword(userDetailsDto.hashedPassword()).
                roles(userDetailsDto.roles()).
                build();
    }




}
