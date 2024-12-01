package com.sk.backend.application.service.user;

import com.sk.backend.domain.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * packageName    : com.sk.backend.application.service.user
 * fileName       : UserServiceImpl
 * author         : moongi
 * date           : 12/1/24
 * description    :
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepository userRepository;
}
