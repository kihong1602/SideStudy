package com.blanc.spring.config.autoConfig;

import com.blanc.spring.config.MyAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

@MyAutoConfiguration
public class DispatcherServletConfig {

  @Bean
  public DispatcherServlet dispatcherServlet() {
    return new DispatcherServlet();
  }

}
