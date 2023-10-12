package com.itsweb.backend.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String userId;

    @Column(unique = true)
    private String username;

    private String password;

    public void signUp(String userId, String username, String password, PasswordEncoder passwordEncoder) {
        this.userId = userId;
        this.password = passwordEncoder.encode(password);
        this.username = username;
    }

}
