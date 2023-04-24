package com.jingwon.lmssystem.member.repository;

import com.jingwon.lmssystem.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,String> {

    Optional<Member> findByEmailAuthKey(String emailAuthKey);

}
