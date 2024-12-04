package com.sk.backend.domain.dto.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : com.sk.backend.domain.dto.user
 * fileName       : CustomOAuth2User
 * author         : moongi
 * date           : 12/3/24
 * description    :
 */
@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User, UserDetails {

    private final UserDto userDTO;

    @Override
    public Map<String, Object> getAttributes() {

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("id", userDTO.getUserId());
        attributes.put("username", userDTO.getUsername());
        attributes.put("role", userDTO.getRole());

        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add((GrantedAuthority) userDTO::getRole);

        return collection;
    }

    @Override
    public String getPassword() {
        return userDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return userDTO.getUsername();
    }

    @Override
    public String getName() {
        return userDTO.getUsername();
    }

    public String getRole() {
        return userDTO.getRole();
    }
}
