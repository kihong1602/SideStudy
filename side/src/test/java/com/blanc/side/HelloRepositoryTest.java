package com.blanc.side;

import com.blanc.side.toby.repository.HelloRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@SidebootTest
public class HelloRepositoryTest {

  @Autowired
  HelloRepository helloRepository;
  @Autowired
  JdbcTemplate jdbcTemplate;

  @BeforeEach
  void init() {
    jdbcTemplate.execute("create table if not exists hello(name varchar(50) primary key, count int)");
  }

  @Test
  void findHelloFailed() {
    Assertions.assertThat(helloRepository.findHello("Blanc")).isNull();
  }

  @Test
  void increaseCount() {
    Assertions.assertThat(helloRepository.countOf("Blanc")).isEqualTo(0);

    helloRepository.increaseCount("Blanc");
    Assertions.assertThat(helloRepository.countOf("Blanc")).isEqualTo(1);

    helloRepository.increaseCount("Blanc");
    Assertions.assertThat(helloRepository.countOf("Blanc")).isEqualTo(2);

  }
}
