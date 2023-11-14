package com.blanc.side;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@SidebootTest
public class JdbcTemplateTest {

  @Autowired
  JdbcTemplate jdbcTemplate;

  @BeforeEach
  void init() {
    jdbcTemplate.execute("create table if not exists hello(name varchar(50) primary key, count int)");
  }

  @Test
  void insertAndQuery() {
    jdbcTemplate.update("insert into hello values(?,?)", "Blanc", 1);
    jdbcTemplate.update("insert into hello values(?,?)", "kihong", 2);
    Long result = jdbcTemplate.queryForObject("select count(*) from hello", Long.class);
    Assertions.assertThat(result).isEqualTo(2);
  }

}