package com.blanc.side.security.service;

import com.blanc.side.security.entity.Member;
import com.blanc.side.security.repository.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

  private final MemberRepository memberRepository;

  public CustomUserDetailsService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  /**
   * 로그인 시 DB에서 유저정보와 권한정보를 가져온 후, 해당 정보를 기반으로 userdetails.User 객체를 생성해서 return
   */
  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return memberRepository.findOneWithAuthoritiesByUsername(username).map(member -> createMember(username, member))
                           .orElseThrow(() -> new UsernameNotFoundException(username + "를 데이터베이스에서 찾을 수 없습니다."));
  }

  private User createMember(String username, Member member) {
    if (!member.isActivated()) {
      throw new RuntimeException(username + " 이 활성화 되어있지 않습니다.");
    }
    List<GrantedAuthority> grantedAuthorities = member.getAuthorities().stream()
                                                      .map(authority -> new SimpleGrantedAuthority(
                                                          authority.getAuthorityName())).collect(Collectors.toList());
    return new User(member.getUsername(), member.getPassword(), grantedAuthorities);
  }
}
