package dev.westernpine.lib.events;

import dev.westernpine.eventapi.objects.Event;
import dev.westernpine.lib.objects.SessionProperties;
import dev.westernpine.pipeline.message.Message;

public class MessageEvent extends Event {

    private final SessionProperties properties;
    private final Message message;

    public MessageEvent(SessionProperties properties, Message message) {
        this.properties = properties;
        this.message = message;
    }

    public SessionProperties getProperties() {
        return properties;
    }

    public Message getMessage() {
        return message;
    }
}
