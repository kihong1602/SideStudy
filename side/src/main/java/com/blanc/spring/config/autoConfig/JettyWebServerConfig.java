package com.blanc.spring.config.autoConfig;

import com.blanc.spring.config.ConditionalMyOnClass;
import com.blanc.spring.config.MyAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@MyAutoConfiguration
@ConditionalMyOnClass("org.eclipse.jetty.server.Server")
public class JettyWebServerConfig {

  @Bean("jettyWebServerFactory")
  @ConditionalOnMissingBean
  public ServletWebServerFactory serverFactory() {
    return new JettyServletWebServerFactory();
  }

}
