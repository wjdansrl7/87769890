package com.sk.backend.domain.dto.board;

import lombok.Getter;

/**
 * packageName    : com.sk.backend.domain.dto.board
 * fileName       : BoardCreateRequest
 * author         : moongi
 * date           : 12/1/24
 * description    :
 */
@Getter
public class BoardCreateRequest {

    private String title;
    private String content;
    private String file;

    // TODO: 파일 및 이미지 처리 필요
//    private String attachment;
}
