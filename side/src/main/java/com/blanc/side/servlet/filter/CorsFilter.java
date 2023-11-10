package com.blanc.side.servlet.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@Slf4j
public class CorsFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    log.info("CorsFilter init START");
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    //도메인 허용 설정
    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:8080");
    //요청에 인증 포함여부
    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
    //서버가 허용하는 HTTP Method
    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "*");
    //preflight 요청의 유효기간 설정
    response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
    //서버가 허용하는 헤더 설정
    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
                       String.join(", ", HttpHeaders.ORIGIN, "X-Requested-With",
                                   HttpHeaders.CONTENT_TYPE, HttpHeaders.ACCEPT, HttpHeaders.AUTHORIZATION));

    //요청이 Option 메서드라면, 응답으로 StatusCode 를 200으로
    if (HttpMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())) {
      response.setStatus(HttpStatus.OK.value());
    } else {
      chain.doFilter(req, res);
    }
  }

  @Override
  public void destroy() {
    log.info("CorsFilter init Destroy");
  }
}
