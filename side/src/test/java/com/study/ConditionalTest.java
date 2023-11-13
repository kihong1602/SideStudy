package com.study;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class ConditionalTest {

  @Test
  void conditional() {
    //true
    ApplicationContextRunner contextRunner = new ApplicationContextRunner();
    contextRunner.withUserConfiguration(Config1.class).run(context -> {
      Assertions.assertThat(context).hasSingleBean(MyBean.class);
      Assertions.assertThat(context).hasSingleBean(Config1.class);
    });

    //false
    ApplicationContextRunner contextRunner2 = new ApplicationContextRunner();
    contextRunner.withUserConfiguration(Config2.class).run(context -> {
      Assertions.assertThat(context).doesNotHaveBean(MyBean.class);
      Assertions.assertThat(context).doesNotHaveBean(Config2.class);
    });
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  @Conditional(BooleanCondition.class)
  @interface BooleanConditional {

    boolean value();
  }


  @Configuration
  @BooleanConditional(true)
  static class Config1 {

    @Bean
    MyBean mybean() {
      return new MyBean();
    }


  }

  @Configuration
  @BooleanConditional(false)
  static class Config2 {

    @Bean
    MyBean myBean() {
      return new MyBean();
    }

  }

  static class BooleanCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
      Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(BooleanConditional.class.getName());
      return (Boolean) annotationAttributes.get("value");
    }
  }

  static class MyBean {

  }
}
