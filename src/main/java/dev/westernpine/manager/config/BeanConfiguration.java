package dev.westernpine.manager.config;

import dev.westernpine.eventapi.EventManager;
import dev.westernpine.manager.properties.IdentityProperties;
import dev.westernpine.manager.properties.SqlProperties;
import dev.westernpine.manager.properties.SystemProperties;
import dev.westernpine.sql.Sql;
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
    public Sql sql() throws Throwable {
        SqlProperties sqlProperties = new SqlProperties(systemProperties.get(SystemProperties.IDENTITY));
        Sql sql = sqlProperties.toSql();
        return sql;
    }

    @Bean
    public EventManager eventManager() {
        return new EventManager();
    }

}
