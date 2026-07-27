package com.example.accountbook.service;

import com.example.accountbook.mapper.MemberMapper;
import com.example.accountbook.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //시큐리티에서 로그인 하면 이 메소드로 유저를 찾음 => 유저를 찾아서 리턴까지 해줌
        Member member = memberMapper.findByUsername(username);
        if (member == null) { //못찾았을 경우 예외처리!
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username );
        }
        //최종 찾은 멤버의 정보로 유저객체를 만들어서 리턴함.(롤 포함)
        return User.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .roles(member.getRole().replace("ROLE_", ""))
                .build();
    }
}
