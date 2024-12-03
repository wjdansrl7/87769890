package com.sk.backend.application.service.user;

import com.sk.backend.domain.dto.user.LoginRequest;
import com.sk.backend.domain.dto.user.LoginResponse;
import com.sk.backend.domain.entity.User;

/**
 * packageName    : com.sk.backend.application.service.user
 * fileName       : UserService
 * author         : moongi
 * date           : 12/1/24
 * description    :
 */
public interface UserService {
    Long saveUser(LoginRequest userCreateDto);
    // 유저 로그인
    LoginResponse login(LoginRequest loginRequest);

    void logout();

    User selectLoginUser(Long id);

}
