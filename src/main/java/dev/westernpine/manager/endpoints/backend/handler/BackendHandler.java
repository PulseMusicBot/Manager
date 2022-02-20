package dev.westernpine.manager.endpoints.backend.handler;

import dev.westernpine.lib.objects.SessionProperties;
import dev.westernpine.manager.security.verification.TokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class BackendHandler extends TextWebSocketHandler {

    @Autowired
    private TokenVerifier tokenVerifier;

    private Map<WebSocketSession, SessionProperties> sessions = new ConcurrentHashMap<>();

    public Map<WebSocketSession, SessionProperties> getSessions() {
        return sessions;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        SessionProperties properties = new SessionProperties(session);
        properties.setIdentity(tokenVerifier.getIdentity(session.getHandshakeHeaders().get("Authorization").get(0)));
        sessions.put(session, properties);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        sessions.get(session).getPipeline().received(message.getPayload());
    }

}
