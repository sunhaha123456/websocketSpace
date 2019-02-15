package com.springboot.websocket;

import com.springboot.util.MessageSendUtil;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint(value = "/messageSend")
public class MessageSendWebsocket {

    // 每个客户端都会有相应的session,服务端可以发送相关消息
    private Session session;

    // 功能：打开连接。进入页面后会自动发请求到此进行连接
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        MessageSendUtil.websocketSet.add(this);
        System.out.println("websocket有新的连接，总数：" + MessageSendUtil.websocketSet.size());
    }

    // 功能：用户关闭页面（即关闭连接）时触发
    @OnClose
    public void onClose() {
        try {
            MessageSendUtil.closeSession(this.getSession());
            MessageSendUtil.websocketSet.remove(this);
            System.out.println("websocket有连接断开，总数：" + MessageSendUtil.websocketSet.size());
        } catch (Exception e) {
            System.out.println("关闭链接时发生错误，错误信息：" + e);
        }
    }

    // 功能：接收客户端发送来的消息
    @OnMessage
    public void onMessage(String message) {
        System.out.println("websocket收到客户端发来的消息：" + message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误，错误信息：" + error);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}