package com.blanc.side.security.config;

import com.blanc.side.security.exception.JwtAccessDeniedHandler;
import com.blanc.side.security.exception.JwtAuthenticationEntryPoint;
import com.blanc.side.security.jwt.JwtFilter;
import com.blanc.side.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 * 메서드를 통한 PreAuthorize를 사용하기 위해서는 EnableMethodSecurity를 사용해야함.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  private final TokenProvider tokenProvider;
  private final JwtAuthenticationEntryPoint authenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

  public SecurityConfig(TokenProvider tokenProvider, JwtAuthenticationEntryPoint authenticationEntryPoint,
      JwtAccessDeniedHandler jwtAccessDeniedHandler) {
    this.tokenProvider = tokenProvider;
    this.authenticationEntryPoint = authenticationEntryPoint;
    this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity security, HandlerMappingIntrospector introspector)
      throws Exception {
    MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

    return security.csrf(AbstractHttpConfigurer::disable)
                   .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                   .exceptionHandling(exceptionHandling -> exceptionHandling
                       .accessDeniedHandler(jwtAccessDeniedHandler)
                       .authenticationEntryPoint(authenticationEntryPoint)
                   )
                   .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                       .requestMatchers(
                           mvcMatcherBuilder.pattern("/api/authenticate"),
                           mvcMatcherBuilder.pattern("/api/signup")
                       ).permitAll().anyRequest().authenticated()
                   ).build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer(HandlerMappingIntrospector introspector) throws Exception {
    MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
    return web -> web.ignoring()
                     .requestMatchers(mvcMatcherBuilder.pattern("/favicon.ico"))
                     .requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
  }
}
