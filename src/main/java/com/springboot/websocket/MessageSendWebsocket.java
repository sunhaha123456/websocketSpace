package com.springboot.websocket;

import com.springboot.util.MessageSendUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@Slf4j
@Component
@ServerEndpoint(value = "/messageSend")
public class MessageSendWebsocket {

    // 功能：打开连接。进入页面后会自动发请求到此进行连接
    @OnOpen
    public void onOpen(Session session) {
        MessageSendUtil.websocketMap.put(session, session.getId());
        log.info("websocket有新的链接，sessionId：" + session.getId() + "，总链接数变为：" + MessageSendUtil.websocketMap.size());
    }

    // 功能：用户关闭页面（即关闭连接）时触发
    @OnClose
    public void onClose(Session session) {
        try {
            MessageSendUtil.closeSession(session);
            log.info("websocket有链接断开，sessionId：" + session.getId() + "，总链接数变为：" + MessageSendUtil.websocketMap.size());
        } catch (Exception e) {
            log.error("websocket关闭链接时发生错误，错误信息：" + e);
        }
    }

    // 功能：接收客户端发送来的消息
    @OnMessage
    public void onMessage(Session session, String message) {
        log.info("sessionId：" + session.getId() + "，接受到消息：" + message);
    }

    /**
     * 功能：异常信息处理
     * 备注：比如，使用 ng 做 ws 集群负载时，在未做心跳情况下，容易导致 ng 自身超时，
     *      当 ng 链接超时，会导致触发该方法，并报 java.io.EOFException，并在此后，触发 onClose 方法，关闭该 session
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("sessionId：" + session.getId() + "，发生错误，错误信息：" + error);
    }
}