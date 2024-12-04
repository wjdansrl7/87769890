package com.sk.backend.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * packageName    : com.sk.backend.common.exception
 * fileName       : ErrorResponse
 * author         : moongi
 * date           : 12/2/24
 * description    :
 */
@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    private final HttpStatus status;
    private final String message;
}
