package com.sk.backend.application.service.board;

import com.sk.backend.domain.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * packageName    : com.sk.backend.application.service.board
 * fileName       : BoardServiceImpl
 * author         : moongi
 * date           : 12/1/24
 * description    :
 */
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

}
