package smvulweb.vulweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import smvulweb.vulweb.domain.member.Member;
import smvulweb.vulweb.repository.MemberRepository;

@RequiredArgsConstructor
@Service
public class MemberDetailService implements UserDetailsService {
    /**Spring Security에서 사용하는 UserDetailService 인터페이스를 구현한 클래스
     * loadUserByUsername 메서드를 통해 사용자 이름을 기반으로 사용자 정보를 찾는데, 이는
     * 인증 및 인가 과정에서 사용
    **/
    private final MemberRepository memberRepository;

    @Override
    public Member loadUserByUsername(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException((email)));
    }
}
