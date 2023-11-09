package com.blanc.side;

import com.blanc.side.toby.controller.HelloController;
import com.blanc.side.toby.service.HelloService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


public class SideApplication {

  public static void main(String[] args) {
    ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
    WebServer webServer = serverFactory.getWebServer(servletContext -> {

      HelloController helloController = new HelloController(new HelloService());
      servletContext.addServlet("hello", new HttpServlet() {
        @Override
        protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          if (request.getRequestURI().equals("/hello") && request.getMethod().equals(HttpMethod.GET.name())) {
            String name = request.getParameter("name");

            String helloMsg = helloController.hello(name);

            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.TEXT_PLAIN_VALUE);
            response.getWriter().println(helloMsg);
          } else {
            response.setStatus(HttpStatus.NOT_FOUND.value());
          }
        }
      }).addMapping("/*");
    });
    webServer.start();
  }

}
