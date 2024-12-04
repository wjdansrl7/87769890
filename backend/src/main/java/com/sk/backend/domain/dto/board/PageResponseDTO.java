package com.sk.backend.domain.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * packageName    : com.sk.backend.domain.dto.board
 * fileName       : PageResponseDTO
 * author         : moongi
 * date           : 12/4/24
 * description    :
 */
@Getter
@AllArgsConstructor
@Builder
public class PageResponseDTO<T> {
    private List<T> content;        // 실제 데이터 리스트
    private int pageNumber;         // 현재 페이지 번호
    private int pageSize;           // 페이지 크기
    private long totalElements;     // 전체 데이터 개수
    private int totalPages;         // 전체 페이지 수
    private boolean last;           // 마지막 페이지 여부
    private boolean first;           // 첫번째 페이지 여부

}
