package com.sk.backend.application.controller;

import com.sk.backend.application.service.user.UserService;
import com.sk.backend.domain.dto.user.LoginRequest;
import com.sk.backend.domain.dto.user.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.login(loginRequest);

        if (loginResponse != null) {
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(loginResponse, HttpStatus.NOT_FOUND);
        }
    }



}
