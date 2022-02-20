package dev.westernpine.manager.endpoints.backend.interceptor;

import dev.westernpine.bettertry.Try;
import dev.westernpine.manager.security.verification.TokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class BackendInterceptor implements HandshakeInterceptor {

    @Autowired
    private TokenVerifier tokenVerifier;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String token = request.getHeaders().get("Authorization").get(0);

        if(token == null) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;
        }

        Try<String> identity = Try.to(() -> tokenVerifier.getIdentity(token));
        if(!identity.isSuccessful()) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return false;
        }

        response.setStatusCode(HttpStatus.SWITCHING_PROTOCOLS);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
