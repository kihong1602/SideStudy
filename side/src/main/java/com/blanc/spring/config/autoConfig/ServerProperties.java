package com.blanc.spring.config.autoConfig;

import com.blanc.spring.config.MyConfigurationProperties;
import org.springframework.beans.factory.annotation.Value;

@MyConfigurationProperties(prefix = "server")
public class ServerProperties {

  @Value("${server.contextPath}")
  private String contextPath;

  @Value("${server.port}")
  private int port;

  public String getContextPath() {
    return contextPath;
  }

  public void setContextPath(String contextPath) {
    this.contextPath = contextPath;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

}
