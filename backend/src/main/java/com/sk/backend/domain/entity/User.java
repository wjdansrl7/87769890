package com.sk.backend.domain.entity;

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
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;

    private String password;

}
