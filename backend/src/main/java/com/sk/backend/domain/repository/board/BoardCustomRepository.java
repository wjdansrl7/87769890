package com.sk.backend.domain.repository.board;

import com.sk.backend.domain.dto.board.BoardCardResponse;
import com.sk.backend.domain.dto.board.BoardDetailResponse;
import com.sk.backend.domain.dto.board.BoardSearchFilter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * packageName    : com.sk.backend.domain.repository.board
 * fileName       : BoardCustomRepository
 * author         : moongi
 * date           : 12/1/24
 * description    :
 */
public interface BoardCustomRepository {

    BoardDetailResponse findBoardByIdAndDeletedAtNull(Long id);

    Page<BoardCardResponse> findBoardsByFilterAndLoginUser(BoardSearchFilter boardSearchFilter);

}
