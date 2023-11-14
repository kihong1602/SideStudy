package com.blanc.side;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SidebootTest
public class DataSourceTest {

  @Autowired
  DataSource dataSource;

  @Test
  void connect() throws SQLException {
    Connection conn = dataSource.getConnection();
    conn.close();
  }

}