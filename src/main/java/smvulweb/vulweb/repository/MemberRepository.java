package smvulweb.vulweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smvulweb.vulweb.domain.member.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
