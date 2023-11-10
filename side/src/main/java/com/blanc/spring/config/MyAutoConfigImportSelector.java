package com.blanc.spring.config;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyAutoConfigImportSelector implements DeferredImportSelector {

  @Override
  public String[] selectImports(AnnotationMetadata importingClassMetadata) {
    return new String[]{
        "com.blanc.spring.config.autoConfig.DispatcherServletConfig",
        "com.blanc.spring.config.autoConfig.TomcatWebServerConfig"
    };
  }
}
