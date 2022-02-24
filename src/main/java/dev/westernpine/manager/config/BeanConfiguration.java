package dev.westernpine.manager.config;

import dev.westernpine.eventapi.EventManager;
import dev.westernpine.manager.properties.IdentityProperties;
import dev.westernpine.manager.properties.SystemProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanConfiguration {

    @Autowired
    private SystemProperties systemProperties;

    @Bean
    public IdentityProperties identityProperties() throws Throwable {
        return new IdentityProperties(systemProperties.get(SystemProperties.IDENTITY));
    }

    @Bean
    public EventManager eventManager() {
        return new EventManager();
    }

}
