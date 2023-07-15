package smvulweb.vulweb.dto.member;

import lombok.Getter;
import lombok.Setter;
import smvulweb.vulweb.domain.member.Authority;

@Getter
@Setter
public class AddMemberRequest {
    private String email;
    private String password;
    private String nickname;
    private Authority authority;

}
