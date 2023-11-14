package com.blanc.spring.config.autoConfig;

import com.blanc.spring.config.ConditionalMyOnClass;
import com.blanc.spring.config.EnableMyConfigurationProperties;
import com.blanc.spring.config.MyAutoConfiguration;
import com.blanc.spring.config.MyDataSourceProperties;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Driver;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@MyAutoConfiguration
@ConditionalMyOnClass("org.springframework.jdbc.core.JdbcOperations")
@EnableMyConfigurationProperties(MyDataSourceProperties.class)
@Slf4j
public class DataSourceConfig {

  @Bean
  @ConditionalMyOnClass("com.zaxxer.hikari.HikariDataSource")
  @ConditionalOnMissingBean
  DataSource hikariDataSource(MyDataSourceProperties properties) {
    HikariDataSource dataSource = new HikariDataSource();

    dataSource.setDriverClassName(properties.getDriverClassName());
    dataSource.setJdbcUrl(properties.getUrl());
    dataSource.setUsername(properties.getUsername());
    dataSource.setPassword(properties.getPassword());
    log.info(properties.getUrl() + ", " + properties.getUsername() + ", " + properties.getPassword());
    return dataSource;
  }

  @Bean
  @ConditionalOnMissingBean
  DataSource dataSource(MyDataSourceProperties properties) throws ClassNotFoundException {
    SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

    dataSource.setDriverClass((Class<? extends Driver>) Class.forName(properties.getDriverClassName()));
    dataSource.setUrl(properties.getUrl());
    dataSource.setUsername(properties.getUsername());
    dataSource.setPassword(properties.getPassword());
    log.info(properties.getUrl() + ", " + properties.getUsername() + ", " + properties.getPassword());
    return dataSource;
  }

}
