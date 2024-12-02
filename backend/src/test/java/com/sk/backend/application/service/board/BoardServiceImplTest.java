package com.sk.backend.application.service.board;

import com.sk.backend.domain.dto.board.BoardCardResponse;
import com.sk.backend.domain.dto.board.BoardSearchFilter;
import com.sk.backend.domain.entity.Board;
import com.sk.backend.domain.entity.User;
import com.sk.backend.domain.repository.board.BoardRepository;
import com.sk.backend.domain.repository.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * packageName    : com.sk.backend.application.service.board
 * fileName       : BoardServiceImplTest
 * author         : moongi
 * date           : 12/2/24
 * description    :
 */
@SpringBootTest
@Transactional
class BoardServiceImplTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;
    @BeforeEach
    void setUp() {
        User user = userRepository.save(new User( 1L, "username", "password"));

        for (int i = 1; i <= 50; i++) {
            Board board = Board.builder()
                    .title("Test Title " + i)
                    .content("Test Content " + i)
                    .author(user)
                    .build();
            boardRepository.save(board);
        }
    }
    @Test
    void 페이징을_게시판_글_조회() {
        BoardSearchFilter filter = BoardSearchFilter.builder()
                .page(2)
                .size(10)
                .build();
        Page<BoardCardResponse> boardCardResponses = boardRepository.findBoardsByFilterAndLoginUser(filter);

        for (BoardCardResponse boardCardResponse : boardCardResponses) {
            System.out.println(boardCardResponse.getTitle());
        }


        Assertions.assertEquals(50, boardCardResponses.getTotalElements());

    }
}