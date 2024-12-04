package com.sk.backend.application.service.user;

import com.sk.backend.domain.dto.user.CustomUserDetails;
import com.sk.backend.domain.dto.user.UserDto;
import com.sk.backend.domain.entity.User;
import com.sk.backend.domain.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userData = userRepository.findByUsername(username);
        UserDto userDto = UserDto.builder()
                .username(userData.getUsername())
                .userId(userData.getId())
                .role(userData.getRole())
                .password(userData.getPassword())
                .build();

        return new CustomUserDetails(userDto); // AuthenticationManger 에게 보냄
    }
}
