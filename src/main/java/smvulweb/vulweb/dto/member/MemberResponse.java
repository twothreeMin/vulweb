package smvulweb.vulweb.dto.member;

import lombok.Getter;
import lombok.Setter;
import smvulweb.vulweb.domain.member.Authority;
import smvulweb.vulweb.domain.member.Member;


@Getter
@Setter
public class MemberResponse {
    private String email;
    private String nickname;
    private String picture_url;
    private Authority authority;

    public MemberResponse(String email, String nickname, String picture_url, Authority authority) {
        this.email = email;
        this.nickname = nickname;
        this.picture_url = picture_url;
        this.authority = authority;
    }
}


