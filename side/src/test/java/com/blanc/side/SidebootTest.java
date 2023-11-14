package com.blanc.side;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
/*@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SideApplication.class)
@TestPropertySource("classpath:/application.properties")*/
@SpringBootTest(classes = SideApplication.class)
@Transactional
public @interface SidebootTest {

}
