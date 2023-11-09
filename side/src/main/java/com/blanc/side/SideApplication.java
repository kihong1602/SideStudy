package com.blanc.side;

import com.blanc.side.toby.bootApplication.MySpringbootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan
public class SideApplication {

  public static void main(String[] args) {
    MySpringbootApplication.run(SideApplication.class, args);

  }


}
