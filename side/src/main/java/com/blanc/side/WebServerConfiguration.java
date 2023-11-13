package com.blanc.side;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class WebServerConfiguration {

  @Bean
  ServletWebServerFactory customWebServerFactory() {
    TomcatServletWebServerFactory ServerFactory = new TomcatServletWebServerFactory();
    ServerFactory.setPort(9090);
    return ServerFactory;
  }
}
