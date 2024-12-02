package com.sk.backend.domain.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * packageName    : com.sk.backend.domain.dto.board
 * fileName       : BoardDetailResponse
 * author         : moongi
 * date           : 12/1/24
 * description    :
 */
@Getter
@Builder
@AllArgsConstructor
public class BoardDetailResponse {

    private String title;
    private String writer;
    private LocalDateTime createdAt;
    private Integer viewCnt;
    private String content;


}
