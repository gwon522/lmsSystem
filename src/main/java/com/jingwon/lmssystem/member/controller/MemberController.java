package com.jingwon.lmssystem.member.controller;

import com.jingwon.lmssystem.member.model.MemberInput;
import com.jingwon.lmssystem.member.service.MemberService;
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

    @GetMapping("/login")
    public String login(){

        return "/member/login";
    }
}
