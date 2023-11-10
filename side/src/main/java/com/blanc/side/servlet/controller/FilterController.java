package com.blanc.side.servlet.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class FilterController {


  @GetMapping("/filterData")
  public ResponseEntity<Map<String, Object>> filterData(HttpServletRequest request, @RequestParam String name,
      @RequestParam int age) {
    Map<String, Object> resMap = new HashMap<>();
    resMap.put("name", name);
    resMap.put("age", age);
    log.info(request.getServerName());
    return new ResponseEntity<>(resMap, HttpStatus.OK);
  }

  @GetMapping("/noFilterData")
  public ResponseEntity<Map<String, Object>> filterData(@RequestParam String name, @RequestParam int age) {
    Map<String, Object> resMap = new HashMap<>();
    resMap.put("name", name);
    resMap.put("age", age);

    return new ResponseEntity<>(resMap, HttpStatus.OK);
  }
}
