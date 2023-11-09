package com.blanc.side.toby.controller;

import com.blanc.side.toby.service.HelloService;

public class HelloController {

  private final HelloService helloService;

  public HelloController(HelloService helloService) {
    this.helloService = helloService;
  }

  public String hello(String name) {
    return helloService.sayHello(name);
  }
}
