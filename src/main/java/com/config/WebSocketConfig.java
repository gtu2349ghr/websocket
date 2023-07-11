package com.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnCloudPlatform;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
@Configuration
public class WebSocketConfig {
    @Bean//定义这个配置类就是让他去扫描endpoint注解
    public ServerEndpointExporter serverEndpointExporter(){
     return new ServerEndpointExporter();
    }

}
