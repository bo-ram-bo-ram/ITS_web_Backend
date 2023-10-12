package com.itsweb.backend.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody MemberDTO memberDTO) {
        Member member = new Member();
        member.signUp(memberDTO.getUserId(), memberDTO.getUsername(), memberDTO.getPassword(),bCryptPasswordEncoder);
        return memberService.join(member);
    }

}
