package com.jingwon.lmssystem.domain.member.repository;

import com.jingwon.lmssystem.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,String> {

    Page<Member> findAll(Pageable pageable);
    Optional<Member> findByEmailAuthKey(String emailAuthKey);
    Optional<Member> findByUserIdAndUserName(String userId, String userName);
    Optional<Member> findByResetPwdKey(String uuid);
    Page<Member> findByUserNameContaining(String userName, Pageable pageRequest);
    Page<Member> findByPhoneContaining(String phone, Pageable pageRequest);
    @Query("SELECT m FROM Member m WHERE m.userName LIKE %:keyword% OR m.phone LIKE %:keyword%")
    Page<Member> searchByUserNameOrPhone(@Param("keyword") String keyword, Pageable pageRequest);
}
