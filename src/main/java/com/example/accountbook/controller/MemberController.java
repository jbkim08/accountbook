package com.example.accountbook.controller;

import com.example.accountbook.model.Member;
import com.example.accountbook.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm() {
        return "login"; //로그인 페이지
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "signup"; //가입하기 페이지
    }

    @PostMapping("/signup")
    public String signup(Member member) {
        memberService.join(member); //새 유저 가입
        return "redirect:/login"; // 가입 후 로그인 페이지로 이동
    }
}
