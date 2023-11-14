package com.blanc.side;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/*@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SideApplication.class)
@TestPropertySource("classpath:/application.properties")*/
@SpringBootTest(classes = SideApplication.class)
public class DataSourceTest {

  @Autowired
  DataSource dataSource;

  @Test
  void connect() throws SQLException {
    Connection conn = dataSource.getConnection();
    conn.close();
  }

}
