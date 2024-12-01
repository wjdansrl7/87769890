package com.sk.backend.application.controller;

import com.sk.backend.application.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
