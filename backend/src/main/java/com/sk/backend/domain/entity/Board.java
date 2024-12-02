package com.sk.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * packageName    : com.sk.backend.domain.entity
 * fileName       : Board
 * author         : moongi
 * date           : 12/1/24
 * description    :
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title; // 제목

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author; // 작성자 id

    private Integer viewCnt = 1; // 조회수

    private boolean isFile; // 첨푸파일 여부

    private String file; // 첨부파일

    private String content; // 작성 내용

    @Builder
    public Board(String title, User author, String file, String content) {
        this.title = title;
        this.author = author;
        this.isFile = file != null;
        this.file = file;
        this.content = content;
    }

    public void updateViewCnt() {
        this.viewCnt+=1;
    }
    public void updateBoard(String title, String file, String content) {
        if (title != null) this.title = title;
        if (file != null) {
            this.file = file;
            this.isFile = true;
        }
        if (content != null) this.content = content;
    }

    // 등록일 노출 default 등록일 역순 정렬(최신순)
    // 한 페이지당 10개 목록 기준으로 페이징 처리
    // 제목이나 작성자 ID로 게시글 검색 가능







}
