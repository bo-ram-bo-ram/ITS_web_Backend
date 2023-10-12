package com.itsweb.backend.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(Member member) {
        memberRepository.save(member);
    }

    public boolean isDuplicated(Member member) {
        return memberRepository.existsByUsername(member.getUsername()) || memberRepository.existsByUserId(member.getUserId());
    }

    public Member loginCheck(String userId, String password) {
        Member findMember = memberRepository.findByUserId(userId);
        //아이디 오류
        if (findMember == null) {
            return null;
        }
        String encodedPassword = findMember.getPassword();
        //비밀번호 오류
        if (!passwordEncoder.matches(password,encodedPassword)) {
            return null;
        }
        return findMember;
    }

    @Transactional
    public ResponseEntity<?> join(Member member) {
        if (isDuplicated(member)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("아이디 또는 닉네임 중복입니다");
        }
        save(member);
        return ResponseEntity.ok().body(member.getId());
    }
}
