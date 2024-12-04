package com.sk.backend.common.util;

import com.sk.backend.domain.entity.Board;
import com.sk.backend.domain.entity.User;
import com.sk.backend.domain.repository.board.BoardRepository;
import com.sk.backend.domain.repository.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.sk.backend.common.util
 * fileName       : DBInitializer
 * author         : moongi
 * date           : 12/4/24
 * description    :
 */
@Component
@RequiredArgsConstructor
public class DBInitializer {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if (boardRepository.count() >= 100) {
            return;
        }

        User user1 = userRepository.save(new User("username1", passwordEncoder.encode("1234"), "ROLE_USER"));
        User user2 = userRepository.save(new User("username2", passwordEncoder.encode("1234"), "ROLE_USER"));
        User user3 = userRepository.save(new User("username3", passwordEncoder.encode("1234"), "ROLE_USER"));

        for (int i = 1; i <= 100; i++) {
            User author = switch (i % 3) {
                case 1 -> user1;
                case 2 -> user2;
                default -> user3;
            };

            Board board = Board.builder()
                    .title("게시글 제목 " + i)
                    .author(author)
                    .content("게시글 내용 " + i)
                    .build();

            boardRepository.save(board);
        }
    }
}
