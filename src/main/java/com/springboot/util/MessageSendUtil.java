package com.springboot.util;

import javax.websocket.Session;
import java.util.concurrent.CopyOnWriteArraySet;

public class MessageSendUtil {

    //J.U.C包下线程安全的类，主要用来存放每个客户端对应的webSocket连接
    public static CopyOnWriteArraySet<Session> websocketSet = new CopyOnWriteArraySet<Session>();

    // 群发消息
    public static void sendMessageAll(String message) {
        for (Session session : websocketSet) {
            try {
                session.getBasicRemote().sendText("sessionId：" + session.getId() + "，发送消息：" + message);
            } catch (Exception e) {
                System.out.println("群发消息错误，错误信息：" + e);
            }
        }
    }

    public static void closeSession(Session session) {
        try {
            if (session != null && !session.isOpen()) {
                session.close();
                websocketSet.remove(session);
            }
        } catch (Exception e) {
            System.out.println("websocket 关闭 session报错，错误信息：" + e);
        }
    }
}