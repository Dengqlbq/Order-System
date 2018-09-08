package com.deng.order.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @description: WebSocket基础服务
 * @author: Deng
 * @create: 2018/9/8
 */
@Component
public class WebSocketHelper {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
