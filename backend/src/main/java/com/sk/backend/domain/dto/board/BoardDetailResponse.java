package com.sk.backend.domain.dto.board;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private Long id;
    private String title;
    private String writer;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH:mm")
    private LocalDateTime createdAt;
    private Integer viewCnt;
    private String content;
    private String file;


}
