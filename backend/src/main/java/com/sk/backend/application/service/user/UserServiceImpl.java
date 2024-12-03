package com.sk.backend.application.service.user;

import com.sk.backend.domain.dto.user.LoginRequest;
import com.sk.backend.domain.dto.user.LoginResponse;
import com.sk.backend.domain.entity.User;
import com.sk.backend.domain.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.sk.backend.application.service.user
 * fileName       : UserServiceImpl
 * author         : moongi
 * date           : 12/1/24
 * description    :
 */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordService passwordService;

    @Override
    public Long saveUser(LoginRequest userCreateDto) {
        String hashPwd = passwordService.encode(userCreateDto.getPassword());
        String role = "ROLE_USER"; // 기본적으로 USER 권한 추가
        User user = User.builder()
                .username(userCreateDto.getUsername())
                .password(hashPwd)
                .role(role)
                .build();
        return userRepository.save(user).getId();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User loginUser = userRepository.findByUsername(loginRequest.getUsername());

        // db에 없는 유저
        if (loginUser == null) {
            // TODO: validator -> 유저의 아이디를 확인하거나, 등록되지 않은 아이디입니다.
            return null;
        }

        // 로그인 성공
        if (loginUser.getPassword().equals(loginRequest.getPassword())) {
            return LoginResponse.builder()
                    .id(loginUser.getId())
                    .username(loginUser.getUsername())
                    .build();

        } else {
            // TODO: 패스워드를 다시 확인해주세요.
            return null;
        }
    }

    // TODO: 토큰 방식 활용하기
    @Override
    public void logout() {

    }

    @Override
    public User selectLoginUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 유저가 업습니다."));
    }
}
