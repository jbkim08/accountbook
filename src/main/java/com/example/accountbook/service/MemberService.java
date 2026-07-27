package com.example.accountbook.service;

import com.example.accountbook.mapper.MemberMapper;
import com.example.accountbook.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    public void join(Member member) {
        // 비밀번호 암호화 (BCrypt)
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        member.setRole("ROLE_USER"); // 기본 권한 설정
        memberMapper.insert(member);
    }
}
