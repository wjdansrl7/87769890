package com.sk.backend.domain.dto.user;

import lombok.Builder;
import lombok.Getter;

/**
 * packageName    : com.sk.backend.domain.dto.user
 * fileName       : UserDetailResponse
 * author         : moongi
 * date           : 12/4/24
 * description    :
 */
@Getter
@Builder
public class UserDetailResponse {
    private String username;
    private String role;
}
