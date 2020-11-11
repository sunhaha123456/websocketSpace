package com.springboot.websocket;

import com.springboot.util.MessageSendUtil;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint(value = "/messageSend")
public class MessageSendWebsocket {

    // 功能：打开连接。进入页面后会自动发请求到此进行连接
    @OnOpen
    public void onOpen(Session session) {
        MessageSendUtil.websocketMap.put(session, session.getId());
        System.out.println("websocket有新的链接，sessionId：" + session.getId() + "，总链接数变为：" + MessageSendUtil.websocketMap.size());
    }

    // 功能：用户关闭页面（即关闭连接）时触发
    @OnClose
    public void onClose(Session session) {
        try {
            MessageSendUtil.closeSession(session);
            System.out.println("websocket有链接断开，sessionId：" + session.getId() + "，总链接数变为：" + MessageSendUtil.websocketMap.size());
        } catch (Exception e) {
            System.out.println("websocket关闭链接时发生错误，错误信息：" + e);
        }
    }

    // 功能：接收客户端发送来的消息
    @OnMessage
    public void onMessage(Session session, String message) {
        System.out.println("sessionId：" + session.getId() + "，接受到消息：" + message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("sessionId：" + session.getId() + "，发生错误，错误信息：" + error);
    }
}