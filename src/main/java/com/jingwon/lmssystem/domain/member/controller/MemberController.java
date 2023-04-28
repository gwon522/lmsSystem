package com.jingwon.lmssystem.domain.member.controller;

import com.jingwon.lmssystem.domain.member.service.MemberService;
import com.jingwon.lmssystem.domain.member.model.MemberInput;
import com.jingwon.lmssystem.domain.member.model.ResetPwd;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/register")
    public String register(){

        return "/member/register";
    }
    @PostMapping("/register")
    public String registerSubmit(Model model,HttpServletRequest request, HttpServletResponse response, MemberInput memberInput){

        log.info("data check {}", memberInput.toString());
        boolean chk = memberService.register(memberInput);

        model.addAttribute("result",chk);
        return "/member/register_complete";
    }
    @GetMapping("/email-auth")
    public String emailAUth(Model model, HttpServletRequest request){
        String uuid = request.getParameter("id");

        boolean result = memberService.emailAuth(uuid);
        model.addAttribute("result",result);
        return "member/email_auth";
    }

    @RequestMapping("/login")
    public String login(){

        return "/member/login";
    }

    @GetMapping("/info")
    public String myPage(){

        return "/member/info";
    }

    @GetMapping("/find-pwd")
    public String findPwd(){

        return "/member/find_pwd";
    }
    @PostMapping("/find-pwd")
    public String findPwdSubmit(Model model, ResetPwd param){
        boolean result = false;
        try {
            result = memberService.sendResetPwd(param);
        }catch (Exception e){

        }
        model.addAttribute("result", result);
        return "/member/find_pwd_result";
    }
    @GetMapping("/reset-pwd")
    public String resetPwd(Model model, ResetPwd param){

        String uuid = param.getId();
        boolean result = false;
        try{
            result = memberService.checkResetPwd(uuid);
        }catch (Exception e){

        }
        model.addAttribute("result",result);
        return "/member/reset_pwd";
    }
    @PostMapping("/reset-pwd")
    public String resetPwdSubmit(Model model, ResetPwd param){

        boolean result = false;
        try {
            result =memberService.resetPwd(param.getId(),param.getPassword());
        }catch (Exception e){

        }
        model.addAttribute("result",result);

        return "/member/reset_pwd_result";
    }
}
