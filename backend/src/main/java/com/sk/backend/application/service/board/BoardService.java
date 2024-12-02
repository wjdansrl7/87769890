package com.sk.backend.application.service.board;

import com.sk.backend.domain.dto.board.BoardCardResponse;
import com.sk.backend.domain.dto.board.BoardCreateRequest;
import com.sk.backend.domain.dto.board.BoardDetailResponse;
import com.sk.backend.domain.dto.board.BoardSearchFilter;
import com.sk.backend.domain.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * packageName    : com.sk.backend.application.service.board
 * fileName       : BoardService
 * author         : moongi
 * date           : 12/1/24
 * description    :
 */
public interface BoardService {

    // 게시판 글 작성
    Long createBoard(BoardCreateRequest boardCreateRequest, User loginUser);

    // 게시판 글 수정
    Long updateBoard(Long id, BoardCreateRequest boardCreateRequest, User loginUser);

    // 게시판 글 삭제
    void deleteBoard(Long id, User loginUser);

    // 게시판 상세 보기
    BoardDetailResponse getBoard(Long id, User loginUser);

    // 게시판 목록
    Page<BoardCardResponse> getBoardAll(BoardSearchFilter boardSearchFilter, User loginUser);


}
