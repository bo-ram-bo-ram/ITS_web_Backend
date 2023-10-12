package com.itsweb.backend.member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        Member loginMember = memberService.loginCheck(loginDTO.getUserId(), loginDTO.getPassword());
        if (loginMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호 오류입니다.");
        }
        MemberVO memberVO = new MemberVO(loginMember.getUserId(), loginMember.getUsername());

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, memberVO);
        return ResponseEntity.ok().body("로그인");
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok().body("로그아웃");

    }

    @GetMapping("/mypage")
    public ResponseEntity<String> myPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberVO loginMember) {
        if (loginMember == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인이 필요합니다");
        }
        return ResponseEntity.ok().body(loginMember.getUserId());
    }

}
