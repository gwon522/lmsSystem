package com.jingwon.lmssystem.domain.member.service.impl;

import com.jingwon.lmssystem.common.dto.SearchDto;
import com.jingwon.lmssystem.component.MailComponents;
import com.jingwon.lmssystem.domain.member.entity.Member;
import com.jingwon.lmssystem.domain.member.excetion.MemberNotEmailAuthException;
import com.jingwon.lmssystem.domain.member.repository.MemberRepository;
import com.jingwon.lmssystem.domain.member.service.MemberService;
import com.jingwon.lmssystem.domain.member.model.MemberInput;
import com.jingwon.lmssystem.domain.member.model.ResetPwd;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
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
                .pwd(BCrypt.hashpw(memberInput.getPwd(),BCrypt.gensalt()))
                .phone(memberInput.getPhone())
                .regDt(LocalDateTime.now())
                .emailAuthYn(false)
                .emailAuthKey(uuid)
                .build();

        mailComponents.sendMail(member.getUserId()
                ,"사이트가입을 위한 인증"
                ,"<p>사이트 가입을 축하드립니다.</p><p>아래 링크를 클릭하셔서 가입을 완료하세요.</p>"
                +"<div><a target='blank_' href='http://localhost:8080/member/hemail-aut?id="+uuid+"'>사이트 이동</a></div>");


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

        if(member.isEmailAuthYn()){
            return false;
        }

        member.setEmailAuthYn(true);
        member.setEmailAuthDt(LocalDateTime.now());

        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean sendResetPwd(ResetPwd param) {
        Optional<Member> optionalMember = memberRepository.findByUserIdAndUserName(param.getUserId(),param.getUserName());

        Member member = optionalMember.orElseThrow(() -> new UsernameNotFoundException("회원정보가 존재하지 않습니다."));
        String uuid = UUID.randomUUID().toString();

        member.setResetPwdKey(uuid);
        member.setResetPwdLimitDt(LocalDateTime.now().plusDays(1));
        memberRepository.save(member);


        mailComponents.sendMail(member.getUserId()
                ,"[lmsSystem] 비밀번호 초기화 메일"
                ,"<p>비밀번호 초기화 메일</p><p>아래 링크를 클릭하셔서 비밀번호를 초기화 해주세요.</p>"
                        +"<div><a target='blank_' href='http://localhost:8080/member/reset_pwd?id="+uuid+"'>비밀번호 초기화</a></div>");

        return true;
    }

    @Override
    public boolean resetPwd(String id, String password) {
        Optional<Member> optionalMember = memberRepository.findByResetPwdKey(id);
        Member member = optionalMember.orElseThrow(()-> new UsernameNotFoundException("회원정보가 존재하지 않습니다"));

        if(member.getResetPwdLimitDt()==null || member.getResetPwdLimitDt().isBefore(LocalDateTime.now())){
            throw  new RuntimeException("유효한 날짜가 아닙니다.");
        }
        String encPwd = BCrypt.hashpw(password,BCrypt.gensalt());
        member.setPwd(encPwd);
        member.setResetPwdKey("");
        member.setResetPwdLimitDt(null);
        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean checkResetPwd(String uuid) {
        Optional<Member> optionalMember = memberRepository.findByResetPwdKey(uuid);

        Member member = optionalMember.orElseThrow(()-> new UsernameNotFoundException("회원정보가 존재하지 않습니다"));
        if(member.getResetPwdLimitDt()==null || member.getResetPwdLimitDt().isBefore(LocalDateTime.now())){
            throw  new RuntimeException("유효한 날짜가 아닙니다.");
        }

        return true;
    }


    @Override
    public Page<Member> list(SearchDto searchDto, Pageable pageable) {
        log.info(searchDto.getSearchType() );
        if(searchDto.getSearchType() == null){
            return memberRepository.findAll(pageable);
        }
        String searchValue =searchDto.getSearchValue();
        switch (searchDto.getSearchType()){
            case "phone":
                return memberRepository.findByPhoneContaining(searchValue,pageable);
            case "name":
                return memberRepository.findByUserNameContaining(searchValue,pageable);
            default:
                return memberRepository.searchByUserNameOrPhone(searchValue,pageable);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findById(username);

        Member member = optionalMember.orElseThrow(()-> new UsernameNotFoundException("회원정보가 존재하지 않습니다."));
        if(!member.isEmailAuthYn()){
            throw new MemberNotEmailAuthException("이메일 활성화가 되지 않았습니다.");
        }
        String role = "USER";
        if(member.isAdminYn()){
            role = "ADMIN";
        }

        return User.builder().username(member.getUserName()).password(member.getPwd()).roles(role).build();
    }
}
