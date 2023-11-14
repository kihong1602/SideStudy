package com.blanc.spring.config.autoConfig;

import com.blanc.spring.config.MyAutoConfiguration;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@MyAutoConfiguration
public class ServerPropertiesConfig {

  @Bean
  public ServerProperties serverProperties(Environment environment) {

    return Binder.get(environment).bind("", ServerProperties.class).get();

  }

}
