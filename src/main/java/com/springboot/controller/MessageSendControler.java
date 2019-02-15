package com.springboot.controller;

import com.springboot.util.MessageSendUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MessageSendControler {

    @GetMapping(value = "/sendMessageAll")
    public String sendMessageAll() throws Exception {
        MessageSendUtil.sendMessageAll("客户端你好！");
        return "发送成功";
    }
}