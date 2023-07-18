package smvulweb.vulweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smvulweb.vulweb.domain.member.Member;
import smvulweb.vulweb.global.config.jwt.TokenProvider;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {
    //전달받은 refresh token으로 유효성 검사를 진행하고,
    //유효한 토큰일때 리프레시 토큰으로 사용자 ID를 찾는다.
    //마지막으로 사용자 ID로 사용자를 찾으면 generateToken()으로 새로운 accesstoken을 생성
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final MemberService memberService;

    public String createNewAccessToken(String refreshToken) {
        // 토큰 유효성 검사에 실패하면 예외 발생
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        Member member = memberService.findById(userId);

        return tokenProvider.generateToken(member, Duration.ofHours(2));
    }

    public boolean validToken(String token) {
        return tokenProvider.validToken(token);
    }

}
