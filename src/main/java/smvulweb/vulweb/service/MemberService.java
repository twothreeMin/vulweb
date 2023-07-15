package smvulweb.vulweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import smvulweb.vulweb.domain.member.Authority;
import smvulweb.vulweb.domain.member.Member;
import smvulweb.vulweb.dto.member.AddMemberRequest;
import smvulweb.vulweb.repository.MemberRepository;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;


    public Long save(AddMemberRequest dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return memberRepository.save(Member.builder()
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .authority(Authority.ROLE_USER)
                .build()).getId();
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

}
