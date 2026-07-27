package com.example.accountbook.mapper;

import com.example.accountbook.model.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    // 유저 찾기
    Member findByUsername(@Param("username") String username);
    // 새 유저 추가
    void insert(Member member);

}
