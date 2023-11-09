package com.blanc.side;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


public class SideApplication {

  public static void main(String[] args) {
    ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
    WebServer webServer = serverFactory.getWebServer(servletContext -> {
      servletContext.addServlet("hello", new HttpServlet() {
        @Override
        protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          response.setStatus(HttpStatus.OK.value());
          response.setContentType(MediaType.TEXT_PLAIN_VALUE);
          response.getWriter().write("Hello SpringBoot");
        }
      }).addMapping("/hello");
    });
    webServer.start();
  }

}
