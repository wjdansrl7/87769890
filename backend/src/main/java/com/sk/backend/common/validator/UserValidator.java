package com.sk.backend.common.validator;

import com.sk.backend.domain.entity.Board;
import com.sk.backend.domain.entity.User;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.sk.backend.common.validator
 * fileName       : UserValidator
 * author         : moongi
 * date           : 12/1/24
 * description    :
 */
@Component
public class UserValidator {

    // 로그인 유저 판별
    public void checkLoginUserNonNull(User loginUser) {
        if (loginUser == null) {
//            throw new CustomException(NO_LOGIN_USER);
        }
    }

    // 유저가 아예 존재하지 않는 경우
    public void checkUserNonNull(User user) {
        if (user == null) {
//            throw new CustomException(NO_USER);
        }
    }
    // 작성자와 로그인한 유저가 동일한지 체크
    public boolean isWriter(User loginUser, Board board) {
        User writer = board.getAuthor();
        return loginUser.equals(writer);
    }

    // 자신이 작성한 글이 아닐 경우 예외 처리
    public void checkWriter(User loginUser, Board board) {
        if (!isWriter(loginUser, board)) {
//            throw new CustomException(NO_RECRUIT_WRITER);
        }
    }
}
