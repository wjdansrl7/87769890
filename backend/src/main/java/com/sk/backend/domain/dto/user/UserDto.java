package com.sk.backend.domain.dto.user;

import lombok.Builder;
import lombok.Getter;

/**
 * packageName    : com.sk.backend.domain.dto.user
 * fileName       : UserDto
 * author         : moongi
 * date           : 12/3/24
 * description    :
 */
@Getter
@Builder
public class UserDto {

    private Long userId;
    private String username;
    private String role;
    private String password;
}
