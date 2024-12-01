package com.sk.backend.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * packageName    : com.sk.backend.domain.entity
 * fileName       : Board
 * author         : moongi
 * date           : 12/1/24
 * description    :
 */
@Entity
@Getter
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title; // 제목

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user; // 작성자 id

    private Integer viewCnt; // 조회수

    private boolean isAttachment; // 첨푸파일 여부

    private String attachment; // 첨부파일

    // 등록일 노출 default 등록일 역순 정렬(최신순)
    // 한 페이지당 10개 목록 기준으로 페이징 처리
    // 제목이나 작성자 ID로 게시글 검색 가능







}
