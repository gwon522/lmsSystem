package com.jingwon.lmssystem.domain.member.repository;

import com.jingwon.lmssystem.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,String> {

    Optional<Member> findByEmailAuthKey(String emailAuthKey);
    Optional<Member> findByUserIdAndUserName(String userId, String userName);
    Optional<Member> findByResetPwdKey(String uuid);
    List<Member> findByUserNameContaining(String userName);
    List<Member> findByPhoneContaining(String phone);
    @Query("SELECT m FROM Member m WHERE m.userName LIKE %:keyword% OR m.phone LIKE %:keyword%")
    List<Member> searchByUserNameOrPhone(@Param("keyword") String keyword);
}
