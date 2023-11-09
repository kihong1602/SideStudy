package com.blanc.side.toby.controller;

import com.blanc.side.toby.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HelloController {

  private final HelloService helloService;

  public HelloController(HelloService helloService) {
    this.helloService = helloService;
  }

  @GetMapping("/hello")
  public String hello(String name) {
    if (name == null) {
      throw new NullPointerException();
    } else if (name.isEmpty()) {
      throw new IllegalArgumentException();
    }
    return helloService.sayHello(name);
  }
}
