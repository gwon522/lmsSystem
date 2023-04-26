package com.jingwon.lmssystem.main;

import com.jingwon.lmssystem.component.MailComponents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MailComponents mailComponents;

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/error/denied")
    public String error(){
        return "/error/denied";
    }
}
