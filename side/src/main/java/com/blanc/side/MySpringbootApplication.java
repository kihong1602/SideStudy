package com.blanc.side;

import com.blanc.spring.config.autoConfig.DispatcherServletConfig;
import com.blanc.spring.config.autoConfig.TomcatWebServerConfig;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Configuration
@ComponentScan
@Import({DispatcherServletConfig.class, TomcatWebServerConfig.class})
public @interface MySpringbootApplication {

}
