package com.sk.backend.application.service.board;

import com.sk.backend.common.validator.UserValidator;
import com.sk.backend.domain.dto.board.BoardCardResponse;
import com.sk.backend.domain.dto.board.BoardCreateRequest;
import com.sk.backend.domain.dto.board.BoardDetailResponse;
import com.sk.backend.domain.dto.board.BoardSearchFilter;
import com.sk.backend.domain.entity.Board;
import com.sk.backend.domain.entity.User;
import com.sk.backend.domain.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : com.sk.backend.application.service.board
 * fileName       : BoardServiceImpl
 * author         : moongi
 * date           : 12/1/24
 * description    :
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final UserValidator userValidator;

    /**
     * 로그인 검증은 인증(Authentication)의 역할
     * DB 존재 여부는 비즈니스 로직 검증의 일부
     * 책임 분리의 원칙
     */
    @Transactional
    @Override
    public Long createBoard(BoardCreateRequest boardCreateRequest, User loginUser) {
        // 해당 유저가 DB에 존재하는지 검증
        userValidator.checkUserNonNull(loginUser);

        Board board = Board.builder()
                .author(loginUser)
                .title(boardCreateRequest.getTitle())
                .file(boardCreateRequest.getFile())
                .content(boardCreateRequest.getContent())
                .build();

        boardRepository.save(board);

        return board.getId();

    }

    @Transactional
    @Override
    public Long updateBoard(Long id, BoardCreateRequest boardCreateRequest, User loginUser) {
        // 해당 유저가 DB에 존재하는지 검증
        userValidator.checkUserNonNull(loginUser);

        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        // 게시판 글 수정
        board.updateBoard(boardCreateRequest.getTitle(), boardCreateRequest.getFile(), boardCreateRequest.getContent());

        boardRepository.save(board);

        return board.getId();
    }

    @Transactional
    @Override
    public void deleteBoard(Long id, User loginUser) {

        userValidator.checkUserNonNull(loginUser);

        // 게시글의 삭제는 필드로 관리한다.
        Board board = boardRepository.findByIdAndDeletedAtNull(id).orElseThrow(() -> new IllegalArgumentException("해당하는 게시글이 없습니다."));
        board.updateDeletedAt();
    }

    @Transactional
    @Override
    public BoardDetailResponse getBoard(Long id, User loginUser) {
        userValidator.checkUserNonNull(loginUser);

        Board board = boardRepository.findByIdAndDeletedAtNull(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 게시글이 없습니다."));
        board.updateViewCnt();

        return boardRepository.findBoardByIdAndDeletedAtNull(id);
    }

    @Override
    public Page<BoardCardResponse> getBoardAll(BoardSearchFilter boardSearchFilter, User loginUser) {
        userValidator.checkUserNonNull(loginUser);

        return boardRepository.findBoardsByFilterAndLoginUser(boardSearchFilter);
    }
}
