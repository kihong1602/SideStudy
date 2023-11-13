package com.blanc.side.security.controller;

import com.blanc.side.security.dto.UserDto;
import com.blanc.side.security.entity.Member;
import com.blanc.side.security.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MemberController {

  private final MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @PostMapping("/signup")
  public ResponseEntity<Member> signup(@Valid @RequestBody UserDto userDto) {
    return ResponseEntity.ok(memberService.signUp(userDto));
  }

  @GetMapping("/user")
  @PreAuthorize("hasAnyRole('USER','ADMIN')")
  public ResponseEntity<Member> getMyUserInfo() {
    return ResponseEntity.ok(memberService.getMyMemberWithAuthorities().get());
  }

  @GetMapping("/user/{username}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<Member> getUserInfo(@PathVariable String username) {
    return ResponseEntity.ok(memberService.getMemberWithAuthorities(username).get());
  }
}
