package dev.westernpine.manager.endpoints.backend;

import dev.westernpine.eventapi.EventManager;
import dev.westernpine.eventapi.objects.EventHandler;
import dev.westernpine.eventapi.objects.Listener;
import dev.westernpine.lib.events.MessageEvent;
import dev.westernpine.lib.objects.SessionProperties;
import dev.westernpine.pipeline.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BackendRequestListener implements Listener {

    @Autowired
    private BackendHandler backendHandler;

    @Autowired
    private PremiumManager premiumManager;

    private EventManager eventManager;

    public BackendRequestListener(EventManager eventManager) {
        this.eventManager = eventManager;
        this.eventManager.registerListeners(this);
    }

    @EventHandler
    public void onMessageEvent(MessageEvent event) {
        SessionProperties properties = event.getProperties();
        Message message = event.getMessage();
        if (message.isRequest()) {
            switch (message.read().toString()) {
                case "user.premium":
                    properties.getPipeline().send(message.toRespone().write(premiumManager.isPremium(message.read().toString())));
                    break;
                default:
                    properties.getPipeline().send(message.toRespone().write("error").write("Unknown request!"));
                    break;
            }
        } else if (message.isMessage()) {
            switch (message.read().toString()) {
                case "user.premium.update":
                    if (properties.isPremiumMaster())
                        premiumManager.setPremium(message.read().toString(), message.read(Boolean.class));
                    break;
                case "session.premiummaster":
                    if (!backendHandler.getSessions().values().stream().anyMatch(SessionProperties::isPremiumMaster))
                        properties.setPremiumMaster(message.read(Boolean.class));
                    break;
                default:
                    StringBuilder builder = new StringBuilder();
                    while (message.hasNext())
                        builder.append(message.read().toString() + "\n");
                    System.out.println("Received Unknown Message: " + builder.toString());
                    break;
            }
        }
    }

}
