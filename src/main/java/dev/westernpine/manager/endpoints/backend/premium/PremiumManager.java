package dev.westernpine.manager.endpoints.backend.premium;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import dev.westernpine.bettertry.Try;
import dev.westernpine.lib.objects.SessionProperties;
import dev.westernpine.manager.endpoints.backend.handler.BackendHandler;
import dev.westernpine.manager.properties.SqlProperties;
import dev.westernpine.pipeline.message.Message;
import dev.westernpine.pipeline.message.MessageType;
import dev.westernpine.sql.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Component
public class PremiumManager {

    @Autowired
    private BackendHandler backendHandler;

    //Discord userId -> premiumStatus
    //Master premium cache system for entire network.
    //Used userId here because we can always retrieve the owner of a guild, but can't get an owner's guilds.
    private LoadingCache<String, Boolean> premiumCache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
//            .expireAfterAccess(5, TimeUnit.MINUTES) //We exclude this because say the Premium master session is down and we load a false value. This value will be retained every time its accessed. So we just let it time out.
            .build(new CacheLoader<String, Boolean>() {
                @Override
                public Boolean load(String s) {
                    return backendHandler
                            .getSessions()
                            .values()
                            .stream()
                            .filter(SessionProperties::isPremiumMaster)
                            .findAny()
                            .map(properties -> Try.to(() -> properties.getPipeline().send(new Message().withType(MessageType.REQUEST).write("user.premium").write(s)).get(2, TimeUnit.SECONDS))
                                        .map(respone -> respone.read(Boolean.class))
                                        .orElse(false))
                            .orElse(false);
                }
            });

    public boolean isPremium(String userId) {
        return Try.to(() -> premiumCache.get(userId)).orElse(false);
    }

    //Set premium status in local first, then update throughout all other sessions.
    public void setPremium(String userId, boolean premium) {
        premiumCache.put(userId, premium);
        backendHandler.getSessions()
                .values()
                .stream()
                .filter(properties -> !properties.isPremiumMaster())
                .forEach(properties -> Try.to(() -> properties.getPipeline().send(new Message().write("user.premium.update").write(userId).write(premium))));
    }





}
