package smvulweb.vulweb.global.config.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import smvulweb.vulweb.domain.member.Authority;
import smvulweb.vulweb.domain.member.Member;
import smvulweb.vulweb.global.config.utils.CheckManager;
import smvulweb.vulweb.repository.MemberRepository;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        //요청을 바탕으로 유저 정보를 담은 객체 반환
        OAuth2User user = super.loadUser(userRequest);
        saveOrUpdate(user);
        System.out.println(user);
        return user;
    }

    //유저가 있으면 업데이트, 없으면 유저 생성
    private Member saveOrUpdate(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String picture_url = (String) attributes.get("picture"); //사진 저장은 AWS S3 스토리지 서비스 이용하기.
        Authority authority = CheckManager.checkIfManager(email) ? Authority.ROLE_MANAGER : Authority.ROLE_USER;

        Member member = memberRepository.findByEmail(email)
                .map(entity -> entity.update(name))
                .orElse(Member.builder()
                        .email(email)
                        .nickname(name)
                        .authority(authority)
                        .picture_url(picture_url)
                        .build());

        return memberRepository.save(member);
    }
}
