package com.blanc.spring.config.autoConfig;

import com.blanc.spring.config.MyAutoConfiguration;
import com.blanc.spring.config.autoConfig.TomcatWebServerConfig.TomcatContdition;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;

@MyAutoConfiguration
@Conditional(TomcatContdition.class)
public class TomcatWebServerConfig {

  @Bean("tomcatWebServerFactory")
  public ServletWebServerFactory serverFactory() {
    return new TomcatServletWebServerFactory();
  }

  static class TomcatContdition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
      return false;
    }
  }
}
