package com.blanc.spring.config.autoConfig;

import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;
import static org.springframework.core.annotation.AnnotationUtils.getAnnotationAttributes;

import com.blanc.spring.config.MyAutoConfiguration;
import com.blanc.spring.config.MyConfigurationProperties;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@MyAutoConfiguration
public class PropertyPostProcessorConfig {

  @Bean
  BeanPostProcessor propertyProcessor(Environment environment) {
    return new BeanPostProcessor() {
      @Override
      public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        MyConfigurationProperties annotation = findAnnotation(bean.getClass(),
                                                              MyConfigurationProperties.class);
        if (annotation == null) {
          return bean;
        }
        Map<String, Object> attrs = getAnnotationAttributes(annotation);
        String prefix = (String) attrs.get("prefix");

        return Binder.get(environment).bindOrCreate(prefix, bean.getClass());
      }
    };
  }
}
