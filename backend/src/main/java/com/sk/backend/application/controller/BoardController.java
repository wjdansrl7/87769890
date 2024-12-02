package com.sk.backend.application.controller;

import com.sk.backend.application.service.board.BoardService;
import com.sk.backend.application.service.user.UserService;
import com.sk.backend.domain.dto.board.BoardCreateRequest;
import com.sk.backend.domain.dto.board.BoardSearchFilter;
import com.sk.backend.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * packageName    : com.sk.backend.application.controller
 * fileName       : BoardController
 * author         : moongi
 * date           : 12/1/24
 * description    :
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createBoard(@RequestBody BoardCreateRequest boardCreateRequest) {
        User loginUser = userService.selectLoginUser(1L);
        return new ResponseEntity<>(boardService.createBoard(boardCreateRequest, loginUser), HttpStatus.CREATED);
    }

    @PatchMapping("/{board-id}")
    public ResponseEntity<?> updateBoard(@PathVariable("board-id") Long boardId, @RequestBody BoardCreateRequest boardCreateRequest) {
        User loginUser = userService.selectLoginUser(1L);
        return new ResponseEntity<>(boardService.updateBoard(boardId, boardCreateRequest, loginUser), HttpStatus.OK);
    }

    @DeleteMapping("/{board-id}")
    public ResponseEntity<?> deleteBoard(@PathVariable("board-id") Long boardId) {
        User loginUser = userService.selectLoginUser(1L);
        boardService.deleteBoard(boardId, loginUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{board-id}")
    public ResponseEntity<?> getBoard(@PathVariable("board-id") Long boardId) {
        User loginUser = userService.selectLoginUser(1L);
        return new ResponseEntity<>(boardService.getBoard(boardId, loginUser), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getBoardAll(BoardSearchFilter boardSearchFilter) {
        User loginUser = userService.selectLoginUser(1L);
        return new ResponseEntity<>(boardService.getBoardAll(boardSearchFilter, loginUser), HttpStatus.OK);
    }
}
