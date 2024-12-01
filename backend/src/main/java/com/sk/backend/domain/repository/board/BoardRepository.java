package com.sk.backend.domain.repository.board;

import com.sk.backend.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.sk.backend.domain.repository
 * fileName       : BoardRepository
 * author         : moongi
 * date           : 12/1/24
 * description    :
 */
public interface BoardRepository extends JpaRepository<Board, Long> {
}
