package com.ctts.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/login")
@Controller
public class LoginController {

    @GetMapping("/sign-in")
    public String signIn() {
        return "html/sign-in";
    }
}
