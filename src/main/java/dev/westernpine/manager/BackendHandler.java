package dev.westernpine.manager;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class BackendHandler extends TextWebSocketHandler {

    List<WebSocketSession> sessionList = new CopyOnWriteArrayList<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Incoming message from %s: %s".formatted(session.getRemoteAddress(), message.getPayload()));
        for(WebSocketSession webSocketSession : sessionList) {
            webSocketSession.sendMessage(new TextMessage("Hello " + message.getPayload() + " !"));
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionList.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessionList.remove(session);
    }

}
