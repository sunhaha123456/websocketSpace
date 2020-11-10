package com.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class RedisPubSubControler {

    @Inject
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping(value = "/redisPub")
    public Map redisPub() throws Exception {
        Map<String, Object> res = new HashMap<String, Object>();
        stringRedisTemplate.convertAndSend("cat","我是一只猫！");
        stringRedisTemplate.convertAndSend("fish","我是一条鱼！");
        res.put("success", true);
        return res;
    }
}