package com.blanc.side.servlet.config;

import com.blanc.side.servlet.filter.CorsFilter;
import com.blanc.side.servlet.filter.CustomRequestFilter;
import com.blanc.side.servlet.filter.CustomResponseFilter;
import java.util.List;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

  @Bean
  public FilterRegistrationBean<CorsFilter> corsFilter() {
    FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>(new CorsFilter());
    registrationBean.setUrlPatterns(List.of("/*")); //필터 적용 URL
    registrationBean.setOrder(1); //필터 적용 순서

    return registrationBean;
  }

  @Bean
  public FilterRegistrationBean<CustomRequestFilter> customRequestFilter() {
    FilterRegistrationBean<CustomRequestFilter> registrationBean = new FilterRegistrationBean<>(
        new CustomRequestFilter());
    registrationBean.setUrlPatterns(List.of("/*"));
    registrationBean.setOrder(2);

    return registrationBean;
  }

  @Bean
  public FilterRegistrationBean<CustomResponseFilter> customResponseFilter() {
    FilterRegistrationBean<CustomResponseFilter> registrationBean = new FilterRegistrationBean<>(
        new CustomResponseFilter());
    registrationBean.setUrlPatterns(List.of("/*"));
    registrationBean.setOrder(3);

    return registrationBean;
  }
}
