package com.sk.backend.domain.entity;

import com.sk.backend.common.constant.entity_field.Role;
import jakarta.persistence.*;
import lombok.*;

/**
 * packageName    : com.sk.backend.domain.entity
 * fileName       : User
 * author         : moongi
 * date           : 12/1/24
 * description    :
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;

    private String password;

//    @Enumerated(EnumType.STRING)
//    private Role role;
    private String role;

    @Builder
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
