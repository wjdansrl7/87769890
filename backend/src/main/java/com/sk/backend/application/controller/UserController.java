package com.sk.backend.application.controller;

import com.sk.backend.application.service.user.UserService;
import com.sk.backend.domain.dto.user.CustomUserDetails;
import com.sk.backend.domain.dto.user.LoginRequest;
import com.sk.backend.domain.dto.user.LoginResponse;
import com.sk.backend.domain.dto.user.UserDetailResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * packageName    : com.sk.backend.application.controller
 * fileName       : UserController
 * author         : moongi
 * date           : 12/1/24
 * description    :
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody LoginRequest userCreateDto) {
        Long createId = userService.saveUser(userCreateDto);
        return new ResponseEntity<>(createId, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.login(loginRequest);

        if (loginResponse != null) {
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(loginResponse, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        userService.logout(request, response);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        UserDetailResponse userDetailDto = userService.getUserInfo(customUserDetails.getUsername());
        if (userDetailDto != null) {
            return new ResponseEntity<>(userDetailDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


}
