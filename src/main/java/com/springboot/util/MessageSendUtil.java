package com.springboot.util;

import org.springframework.util.StringUtils;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageSendUtil {

    //J.U.C包下线程安全的类，主要用来存放每个客户端对应的webSocket连接
    public static ConcurrentHashMap<Session, Object> websocketMap = new ConcurrentHashMap<Session, Object>();

    // 群发消息
    public static void sendMessageAll(String message) {
        if (!StringUtils.isEmpty(message)) {
            Session session = null;
            for (Map.Entry<Session, Object> entry : websocketMap.entrySet()) {
                try {
                    session = entry.getKey();
                    session.getBasicRemote().sendText(message);
                } catch (Exception e) {
                    System.out.println("群发消息错误，报错session：" + session.getId() + "，错误信息：" + e);
                }
            }
        }
    }

    public static void closeSession(Session session) {
        try {
            if (session != null) {
                session.close();
            }
            websocketMap.remove(session);
        } catch (Exception e) {
            System.out.println("关闭session报错，报错session：" + session.getId() + "，错误信息：" + e);
        }
    }
}