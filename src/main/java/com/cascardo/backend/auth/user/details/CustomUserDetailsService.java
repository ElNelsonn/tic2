package com.cascardo.backend.auth.user.details;

import com.cascardo.backend.auth.dto.CustomUserDetailsDto;
import com.cascardo.backend.models.User;
import com.cascardo.backend.repositories.UserRepository;
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

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado."));

        return CustomUserDetails.builder().
                id(user.getId()).
                email(user.getEmail()).
                name(user.getName()).
                lastName(user.getLastName()).
                hashedPassword(user.getHashedPassword()).
                build();

    }

    public CustomUserDetails loadUser(CustomUserDetailsDto userDetailsDto) {

        return CustomUserDetails.builder().
                id(userDetailsDto.id()).
                email(userDetailsDto.email()).
                name(userDetailsDto.name()).
                lastName(userDetailsDto.lastName()).
                hashedPassword(userDetailsDto.hashedPassword()).
                build();
    }




}
