package dev.westernpine.manager.endpoints.backend.config;

import dev.westernpine.manager.endpoints.backend.handler.BackendHandler;
import dev.westernpine.manager.endpoints.backend.interceptor.BackendInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class BackendConfig implements WebSocketConfigurer {

    @Autowired
    private BackendHandler backendHandler;

    @Autowired
    private BackendInterceptor backendInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(backendHandler, "/backend").addInterceptors(backendInterceptor);
    }

}
