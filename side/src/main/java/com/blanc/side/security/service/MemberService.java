package com.blanc.side.security.service;

import com.blanc.side.security.dto.UserDto;
import com.blanc.side.security.entity.Authority;
import com.blanc.side.security.entity.Member;
import com.blanc.side.security.repository.MemberRepository;
import com.blanc.side.security.utils.SecurityUtil;
import java.util.Collections;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
    this.memberRepository = memberRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public Member signUp(UserDto userDto) {
    if (memberRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
      throw new RuntimeException("이미 가입되어 있는 유저입니다.");
    }

    Authority authority = Authority.builder().authorityName("USER").build();

    Member member = Member.builder().username(userDto.getUsername())
                          .password(passwordEncoder.encode(userDto.getPassword()))
                          .nickname(userDto.getNickName()).authorities(Collections.singleton(authority)).activated(true)
                          .build();

    return memberRepository.save(member);
  }

  @Transactional(readOnly = true)
  public Optional<Member> getMemberWithAuthorities(String username) {
    return memberRepository.findOneWithAuthoritiesByUsername(username);
  }

  @Transactional(readOnly = true)
  public Optional<Member> getMyMemberWithAuthorities() {
    return SecurityUtil.getCurrentUsername().flatMap(memberRepository::findOneWithAuthoritiesByUsername);
  }
}
