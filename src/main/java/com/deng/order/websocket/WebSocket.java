package com.deng.order.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @description: 与后台页面的WebSocket通信
 * @author: Deng
 * @create: 2018/9/8
 */
@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocket {

    private Session session;

    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSockets.add(this);
        log.info("[WebSocket]: 建立新连接，连接总数: {}", webSockets.size());
    }

    @OnClose
    public void onClose() {
        webSockets.remove(this);
        log.info("[WebSocket]: 断开连接，连接总数: {}", webSockets.size());
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("[WebSocket]: 收到消息: {}", message);
    }

    public void sendMessage(String message) {
        log.info("[WebSocket]: 发送消息: {}", message);
        for (WebSocket socket : webSockets) {
            try {
                socket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                log.warn("[WebSocket]: 通信发生异常: {}", e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
