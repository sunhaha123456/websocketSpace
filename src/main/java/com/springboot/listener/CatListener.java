package com.springboot.listener;

import com.springboot.util.MessageSendUtil;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class CatListener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] bytes) {
        System.out.println("Cat 监听到：" + message.toString());
        MessageSendUtil.sendMessageAll(message.toString());
    }
}