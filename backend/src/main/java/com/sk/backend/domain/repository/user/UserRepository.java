package com.sk.backend.domain.repository.user;

import com.sk.backend.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.sk.backend.domain.repository
 * fileName       : UserRepository
 * author         : moongi
 * date           : 12/1/24
 * description    :
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
