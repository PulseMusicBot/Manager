package dev.westernpine.manager;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class BackendConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new BackendHandler(), "/backend").setAllowedOrigins("*");
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        //这是特定的页面才要loginInterceptor
//        registry.addInterceptor(loginInterceptor)
//                .addPathPatterns("/main.html");
//
//        //这是特定的页面不要loginInterceptor
////        registry.addInterceptor(loginInterceptor)
////                .addPathPatterns("/**")
////                .excludePathPatterns("/login", "/validate");
//
//
//        registry.addInterceptor(localeChangeInterceptor);
//    }
//
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/main.html").setViewName("login/success");
//    }

}
