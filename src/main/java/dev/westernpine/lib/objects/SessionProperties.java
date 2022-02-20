package dev.westernpine.lib.objects;

import dev.westernpine.bettertry.Try;
import dev.westernpine.eventapi.EventManager;
import dev.westernpine.lib.events.MessageEvent;
import dev.westernpine.pipeline.Pipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class SessionProperties {

    @Autowired
    private EventManager eventManager;

    private final WebSocketSession webSocketSession;
    private final Pipeline pipeline;
    private String identity = "";
    private boolean isPremiumMaster;

    public SessionProperties(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
        this.pipeline = new Pipeline(payload -> Try.to(() -> webSocketSession.sendMessage(new TextMessage(payload))).getUnchecked(), message -> eventManager.call(new MessageEvent(this, message)));
    }

    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public boolean isPremiumMaster() {
        return isPremiumMaster;
    }

    public void setPremiumMaster(boolean premiumMaster) {
        isPremiumMaster = premiumMaster;
    }
}
