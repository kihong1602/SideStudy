package com.blanc.side;

import com.blanc.side.toby.dto.Hello;
import com.blanc.side.toby.repository.HelloRepository;
import com.blanc.side.toby.service.HelloDecorator;
import com.blanc.side.toby.service.SimpleHelloService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class HelloServiceTest {

  @Test
  void simpleHelloService() {
    SimpleHelloService helloService = new SimpleHelloService(helloRepositoryStub);

    String result = helloService.sayHello("Test");

    Assertions.assertThat(result).isEqualTo("Hello Test");
  }

  private static final HelloRepository helloRepositoryStub = new HelloRepository() {
    @Override
    public Hello findHello(String name) {
      return null;
    }

    @Override
    public void increaseCount(String name) {

    }
  };

    @Test
    void helloDecorator() {
      HelloDecorator helloDecorator = new HelloDecorator(name -> name);
      String result = helloDecorator.sayHello("Test");

      Assertions.assertThat(result).isEqualTo("*Test*");
    }


}
