package com.ctts.member.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/test")
@RestController
public class TestController {

    @GetMapping("/log-test")
    public String logTest() {
        log.info("TEST LOG");
        return "TEST LOG";
    }
}
