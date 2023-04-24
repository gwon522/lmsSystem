package com.jingwon.lmssystem.member.service.impl;

import com.jingwon.lmssystem.component.MailComponents;
import com.jingwon.lmssystem.member.entity.Member;
import com.jingwon.lmssystem.member.model.MemberInput;
import com.jingwon.lmssystem.member.repository.MemberRepository;
import com.jingwon.lmssystem.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MailComponents mailComponents;
    private final MemberRepository memberRepository;

    @Override
    public boolean register(MemberInput memberInput) {

        Optional<Member> optionalMember = memberRepository.findById(memberInput.getUserId());

        if(optionalMember.isPresent()){
            return false;
        }
        String uuid = UUID.randomUUID().toString();
        Member member = Member.builder()
                .userId(memberInput.getUserId())
                .userName(memberInput.getUserName())
                .pwd(memberInput.getPwd())
                .phone(memberInput.getPhone())
                .regDt(LocalDateTime.now())
                .emailAuthYn(false)
                .emailAuthKey(uuid)
                .build();

        mailComponents.sendMail(member.getUserId()
                ,"사이트가입을 위한 인증"
                ,"<p>사이트 가입을 축하드립니다.</p><p>아래 링크를 클릭하셔서 가입을 완료하세요.</p>"
                +"<div><a target='blank_' href='http://localhost:8080/member/email-auth?id="+uuid+"'>사이트 이동</a></div>");


        memberRepository.save(member);
        return true;
    }

    @Override
    public boolean emailAuth(String uuid) {

        Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);
        if(!optionalMember.isPresent()){
            return false;
        }

        Member member = optionalMember.get();
        member.setEmailAuthYn(true);
        member.setEmailAuthDt(LocalDateTime.now());

        memberRepository.save(member);

        return true;
    }
}
