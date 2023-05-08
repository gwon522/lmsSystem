package com.jingwon.lmssystem.domain.member.service;

import com.jingwon.lmssystem.common.dto.SearchDto;
import com.jingwon.lmssystem.domain.member.entity.Member;
import com.jingwon.lmssystem.domain.member.model.MemberInput;
import com.jingwon.lmssystem.domain.member.model.ResetPwd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface MemberService extends UserDetailsService {
    boolean register(MemberInput memberInput);
    boolean emailAuth(String uuid);
    boolean sendResetPwd(ResetPwd param);
    boolean resetPwd(String id, String password);
    boolean checkResetPwd(String uuid);
    Page<Member> list(SearchDto searchDto, Pageable pageable);
}
