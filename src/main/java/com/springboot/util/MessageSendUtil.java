package com.springboot.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
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
                    log.error("群发消息错误，报错session：" + session.getId() + "，错误信息：" + e);
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
            log.error("关闭session报错，报错session：" + session.getId() + "，错误信息：" + e);
        }
    }
}