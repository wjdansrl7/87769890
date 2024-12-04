package com.sk.backend.common.constant.entity_field;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * packageName    : com.sk.backend.common.constant.entity_field
 * fileName       : Role
 * author         : moongi
 * date           : 12/2/24
 * description    :
 */
@Getter
@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String value;

    @Override
    public String getAuthority() {
        return value;
    }
}
