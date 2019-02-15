package com.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class DemoControler {
    @GetMapping(value = "/toDemo")
    public String toDemo() throws Exception {
        return "demo";
    }
}