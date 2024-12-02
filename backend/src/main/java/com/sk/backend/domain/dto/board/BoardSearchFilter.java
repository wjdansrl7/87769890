package com.sk.backend.domain.dto.board;

import lombok.*;

/**
 * packageName    : com.sk.backend.domain.dto.board
 * fileName       : BoardSearchFilter
 * author         : moongi
 * date           : 12/2/24
 * description    :
 */
@Getter
@NoArgsConstructor
@Setter
public class BoardSearchFilter {
    private String title;
    private String author;
    private Integer page = 0;
    private Integer size = 10;

    @Builder
    public BoardSearchFilter(String title, String author, Integer page, Integer size) {
        this.title = title;
        this.author = author;
        this.page = page;
        this.size = size;
    }
}
