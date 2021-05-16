package com.lyn.wiki.service.impl;

import com.lyn.wiki.websocket.WebSocketServer;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class WsServiceImpl {

    @Autowired
    WebSocketServer socketServer;

    @Async
    public void sendInfo(String msg,String logId){
        MDC.put("LOG_ID",logId);
       socketServer.sendInfo(msg);
    }
}
