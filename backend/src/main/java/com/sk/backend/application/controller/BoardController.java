package com.sk.backend.application.controller;

import com.sk.backend.application.service.board.BoardService;
import com.sk.backend.application.service.board.FileService;
import com.sk.backend.application.service.user.UserService;
import com.sk.backend.domain.dto.board.BoardCardResponse;
import com.sk.backend.domain.dto.board.BoardCreateRequest;
import com.sk.backend.domain.dto.board.BoardSearchFilter;
import com.sk.backend.domain.dto.board.PageResponseDTO;
import com.sk.backend.domain.dto.user.CustomUserDetails;
import com.sk.backend.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    private final FileService fileService;

    @PostMapping
    public ResponseEntity<?> createBoard(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                         @RequestParam("title") String title,
                                         @RequestParam("content") String content,
                                         @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        User loginUser = userService.selectLoginUser(customUserDetails.getUsername());

        String filePath = null;
        if (file != null && !file.isEmpty()) {
            filePath = fileService.storeFile(file); // 파일 저장
        }

        BoardCreateRequest boardCreateRequest = BoardCreateRequest.builder()
                .content(content)
                .title(title)
                .file(filePath)
                .build();
        return new ResponseEntity<>(boardService.createBoard(boardCreateRequest, loginUser), HttpStatus.CREATED);
    }

    @PatchMapping("/{board-id}")
    public ResponseEntity<?> updateBoard(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                         @PathVariable("board-id") Long boardId,
                                         @RequestParam("title") String title,
                                         @RequestParam("content") String content,
                                         @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        User loginUser = userService.selectLoginUser(customUserDetails.getUsername());

        String filePath = null;
        if (file != null && !file.isEmpty()) {
            filePath = fileService.storeFile(file); // 파일 저장
        }
        BoardCreateRequest boardCreateRequest = BoardCreateRequest.builder()
                .content(content)
                .title(title)
                .file(filePath)
                .build();

        return new ResponseEntity<>(boardService.updateBoard(boardId, boardCreateRequest, loginUser), HttpStatus.OK);
    }

    @DeleteMapping("/{board-id}")
    public ResponseEntity<?> deleteBoard(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("board-id") Long boardId) {
        User loginUser = userService.selectLoginUser(customUserDetails.getUsername());
        boardService.deleteBoard(boardId, loginUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{board-id}")
    public ResponseEntity<?> getBoard(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("board-id") Long boardId) {
        User loginUser = userService.selectLoginUser(customUserDetails.getUsername());
        return new ResponseEntity<>(boardService.getBoard(boardId, loginUser), HttpStatus.OK);
    }

    @GetMapping("/view")
    public ResponseEntity<?> getBoardAll(BoardSearchFilter boardSearchFilter) {
        PageResponseDTO<BoardCardResponse> boards = boardService.getBoardAll(boardSearchFilter);
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }
}
