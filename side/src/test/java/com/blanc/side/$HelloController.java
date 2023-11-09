package com.blanc.side;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.blanc.side.toby.controller.HelloController;
import com.blanc.side.toby.service.HelloService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(CustomDisplayNameGenerator.class)
public class $HelloController {

  @Mock
  private HelloService helloService;

  @InjectMocks
  private HelloController helloController;

  @Nested
  class $Name {

    @Test
    void $Test() {
      String testName = "Test";
      when(helloService.sayHello(testName)).thenReturn(testName);

      String helloResult = helloController.hello(testName);

      assertThat(helloResult).isEqualTo(testName);
    }

    @Test
    void $Spring() {
      String testName = "Spring";
      when(helloService.sayHello(testName)).thenReturn(testName);

      String helloResult = helloController.hello(testName);
      assertThat(helloResult).isEqualTo(testName);
    }
  }

  @Nested
  class $Null_Empty {

    @Test
    void $Empty() {

      assertThatThrownBy(() -> {
        String result = helloController.hello("");
      }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void $Null() {

      assertThatThrownBy(() -> {
        String result = helloController.hello(null);
      }).isInstanceOf(NullPointerException.class);
    }
  }
}
