package com.jingwon.lmssystem.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin/member")
public class AdminMemberController {

    @GetMapping("/list")
    public String list(){

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
