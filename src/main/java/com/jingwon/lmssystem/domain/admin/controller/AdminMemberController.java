package com.jingwon.lmssystem.domain.admin.controller;

import com.jingwon.lmssystem.common.dto.SearchDto;
import com.jingwon.lmssystem.domain.member.entity.Member;
import com.jingwon.lmssystem.domain.member.model.MemberInput;
import com.jingwon.lmssystem.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/admin/member")
@RequiredArgsConstructor
public class AdminMemberController {

    private final MemberService memberService;

    @GetMapping("/list")
    public String list(Model model, SearchDto param){
        List<Member> members = memberService.list(param);
        model.addAttribute("list",members);

        return "/admin/member/list";
    }

    @GetMapping("/course")
    public String course(){

        return "/admin/member/course";
    }

    @GetMapping("/category")
    public String category(){

        return "/admin/member/category";
    }


}
