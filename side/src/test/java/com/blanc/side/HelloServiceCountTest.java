package com.blanc.side;

import com.blanc.side.toby.repository.HelloRepository;
import com.blanc.side.toby.service.HelloService;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class HelloServiceCountTest {

  @Autowired
  HelloService helloService;
  @Autowired
  HelloRepository helloRepository;

  @Test
  void sayHelloIncreaseCount() {
    IntStream.rangeClosed(1, 10).forEach(count -> {
      helloService.sayHello("Blanc");
      Assertions.assertThat(helloRepository.countOf("Blanc")).isEqualTo(count);
    });
  }
}
