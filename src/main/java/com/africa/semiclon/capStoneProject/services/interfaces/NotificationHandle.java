package com.africa.semiclon.capStoneProject.services.interfaces;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;

public interface NotificationHandle extends WebSocketHandler {
    void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception;
    void sendNotification(WebSocketSession session, String message) throws Exception;
}
