package com.sk.backend.domain.dto.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * packageName    : com.sk.backend.domain.dto.board
 * fileName       : BoardCardResponse
 * author         : moongi
 * date           : 12/1/24
 * description    :
 */
@Getter
//@Builder
public class BoardCardResponse {
    private Long id;
    private String title;
    private String writer;
    private Integer viewCnt;
    private String hasFile;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH:mm")
    private LocalDateTime createdAt;

    public BoardCardResponse(Long id, String title, String writer, Integer viewCnt, boolean hasFile, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.viewCnt = viewCnt;
        this.hasFile = hasFile ? "Y" : "N"; // 변환 로직 추가
        this.createdAt = createdAt;
    }


}
