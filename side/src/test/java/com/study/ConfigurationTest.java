package com.study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class ConfigurationTest {

  @Configuration
  static class MyConfig {

    @Test
    void configuration() {
      AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
      ac.register(MyConfig.class);
      ac.refresh();

      Bean1 bean1 = ac.getBean(Bean1.class);
      Bean2 bean2 = ac.getBean(Bean2.class);
      Assertions.assertThat(bean1.common).isSameAs(bean2.common);
    }
    

    @Bean
    Common common() {
      return new Common();
    }

    @Bean
    Bean1 bean1() {
      return new Bean1(common());
    }

    @Bean
    Bean2 bean2() {
      return new Bean2(common());
    }
  }

  static class Bean1 {

    private final Common common;

    public Bean1(Common common) {
      this.common = common;
    }
  }

  static class Bean2 {

    private final Common common;

    public Bean2(Common common) {
      this.common = common;
    }
  }


  static class Common {

  }
}
