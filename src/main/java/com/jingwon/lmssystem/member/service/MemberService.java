package com.jingwon.lmssystem.member.service;

import com.jingwon.lmssystem.member.model.MemberInput;
import org.springframework.stereotype.Service;


public interface MemberService {
    boolean register(MemberInput memberInput);

    boolean emailAuth(String uuid);
}
