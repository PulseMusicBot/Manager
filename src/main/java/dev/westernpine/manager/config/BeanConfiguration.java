package dev.westernpine.manager.config;

import dev.westernpine.manager.properties.IdentityProperties;
import dev.westernpine.manager.properties.SystemProperties;
import dev.westernpine.manager.security.verification.TokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class BeanConfiguration {

    @Autowired
    public SystemProperties systemProperties;

    public IdentityProperties identityProperties;

    @Bean
    public IdentityProperties identityProperties() throws Throwable {
        return identityProperties == null ? identityProperties = new IdentityProperties(systemProperties.get(SystemProperties.IDENTITY)) : identityProperties;
    }

    @Bean
    public TokenVerifier tokenVerifier() throws Throwable {
        return new TokenVerifier(identityProperties());
    }

}
