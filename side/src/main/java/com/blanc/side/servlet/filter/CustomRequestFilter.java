package com.blanc.side.servlet.filter;

import com.blanc.side.servlet.Log;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;

@Slf4j
public class CustomRequestFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    log.info(Log.REQUEST_CREATE);
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    
    // /filterDate 로 들어온 GET 요청에 대해 필터링
    if (request.getMethod().equals(HttpMethod.GET.name()) && request.getRequestURI().equals("/filterData")) {

      //요청을 위한 커스텀 Wrapper 생성
      CustomRequestWrapper requestWrapper = new CustomRequestWrapper(request) {
        @Override
        public String getServerName() {
          return "test.com";
        }
      };

      //원하는 대로 파라미터 수정
      requestWrapper.setParameter("name", request.getParameter("name"));
      requestWrapper.setParameter("age", request.getParameter("age"));
      requestWrapper.setParameter("user", "1");

      //수정된 요청으로 진행
      log.info("CustomRequestFilter Start");
      chain.doFilter(requestWrapper, res);
      log.info("CustomRequestFilter End");
    } else {
      //필터링된 요청이 아니라면 기존 요청 그대로 다음필터로 전달
      chain.doFilter(req, res);
    }
  }

  @Override
  public void destroy() {
    log.info(Log.REQUEST_DESTROY);
  }
}
