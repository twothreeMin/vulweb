package smvulweb.vulweb.dto.member;

import lombok.Getter;
import lombok.Setter;
import smvulweb.vulweb.domain.member.Authority;

@Getter
@Setter
public class AddMemberRequest {
    private String username;
    private String password;
    private String name;
    private Authority authority;

}
