package com.itsweb.backend.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberControllerTest {
    @Autowired MemberService memberService;
    @Autowired PasswordEncoder passwordEncoder;

    @Test
    void 회원가입성공() {
        Member member = new Member();
        member.signUp("1", "1", "1234", passwordEncoder);

        ResponseEntity<?> responseEntity = memberService.join(member);

        Long memberId = (Long) responseEntity.getBody();
        assertThat(member.getId()).isEqualTo(memberId);
    }

    @Test
    void 중복회원가입() {
        Member member1 = new Member();
        member1.signUp("2", "2", "1234", passwordEncoder);
        Member member2 = new Member();
        member2.signUp("2", "2", "1234", passwordEncoder);

        memberService.join(member1);
        memberService.join(member2);

        fail("중복회원가입");
    }
}