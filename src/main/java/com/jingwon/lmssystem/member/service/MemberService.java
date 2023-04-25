package com.jingwon.lmssystem.member.service;

import com.jingwon.lmssystem.member.model.MemberInput;
import com.jingwon.lmssystem.member.model.ResetPwd;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


public interface MemberService extends UserDetailsService {
    boolean register(MemberInput memberInput);

    boolean emailAuth(String uuid);

    boolean sendResetPwd(ResetPwd param);

    boolean resetPwd(String id, String password);

    boolean checkResetPwd(String uuid);
}
