package com.blanc.side;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class HelloApiTest {

  @Test
  void SuccessHelloApi() {
    TestRestTemplate template = new TestRestTemplate();
    ResponseEntity<String> res = template.getForEntity("http://localhost:8080/hello?name={name}", String.class,
                                                       "Spring");

    assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(res.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)).startsWith(MediaType.TEXT_PLAIN_VALUE);
    assertThat(res.getBody()).isEqualTo("Hello Spring");
  }

  @Test
  void FailHelloApi() {
    TestRestTemplate template = new TestRestTemplate();
    ResponseEntity<String> res = template.getForEntity("http://localhost:8080/hello?name=", String.class);

    assertThat(res.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
