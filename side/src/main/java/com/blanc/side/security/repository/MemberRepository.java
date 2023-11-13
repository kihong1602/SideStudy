package com.blanc.side.security.repository;

import com.blanc.side.security.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

  @EntityGraph(attributePaths = "autorities")
  Optional<Member> findOneWithAuthoritiesByUsername(String username);
}
