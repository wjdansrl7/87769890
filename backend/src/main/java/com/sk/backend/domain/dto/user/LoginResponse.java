package com.sk.backend.domain.dto.user;

import lombok.Builder;
import lombok.Getter;

/**
 * packageName    : com.sk.backend.domain.dto.user
 * fileName       : LoginSuccessResponse
 * author         : moongi
 * date           : 12/1/24
 * description    :
 */
@Getter
@Builder
public class LoginResponse {
    private Long id;
    private String username;

}
