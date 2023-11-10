package com.blanc.side.servlet.filter;

import com.blanc.side.servlet.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

@Slf4j
public class CustomResponseFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    log.info(Log.RESPONSE_CREATE);
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    //필터가 적용되는 요청 경로 확인
    String requestURI = request.getRequestURI();

    // /filterData 경로에 대해서만 응답 값을 변경
    if (requestURI.equals("/filterData")) {
      //컨트롤러 실행 전에 응답을 가로채기 위해 Wrapper 생성
      CustomResponseWrapper responseWrapper = new CustomResponseWrapper(response);

      //필터 체인 실행 (컨트롤러로 진입)
      log.info("CustomResponseFilter Start");
      chain.doFilter(request, responseWrapper);

      //컨트롤러가 생성한 응답 값 가져오기
      Map<String, Object> originalResponse = responseWrapper.getResponseAsMap();

      //JSON형식으로 변환하여 응답값 수정
      originalResponse.put("user", "Y");

      //수정된 응답값을 JSON 형식으로 변환
      String modifiedResponseJson = new ObjectMapper().writeValueAsString(originalResponse);

      //수정된 응답값을 클라이언트로 전송
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.getWriter().write(modifiedResponseJson);
      log.info("CustomResponseFilter End");
    } else {
      //다른 경로에 대해서는 기존 응답 그대로 전달
      chain.doFilter(request, response);
    }
  }


  @Override
  public void destroy() {
    log.info(Log.RESPONSE_DESTROY);
  }
}
