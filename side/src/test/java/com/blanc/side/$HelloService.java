package com.blanc.side;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.blanc.side.toby.service.HelloDecorator;
import com.blanc.side.toby.service.HelloService;
import com.blanc.side.toby.service.SimpleHelloService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(CustomDisplayNameGenerator.class)
public class $HelloService {

  @Nested
  class $SimpleHello {

    @Mock
    SimpleHelloService helloService;

    @Test
    void $Name() {
      String name = "Test";
      when(helloService.sayHello(name)).thenReturn("Hello Test");
      String result = helloService.sayHello(name);
      assertThat(result).isEqualTo("Hello Test");
    }
  }

  @Nested
  class $HelloDeco {

    @Mock
    HelloService helloService;

    @InjectMocks
    HelloDecorator decorator;

    @Test
    void $Name() {
      String name = "Test";
      when(helloService.sayHello(name)).thenReturn(name);
      String result = decorator.sayHello(name);
      assertThat(result).isEqualTo("*Test*");
    }
  }
}
