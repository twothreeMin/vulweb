package smvulweb.vulweb.dto.member;

import lombok.Getter;
import lombok.Setter;
import smvulweb.vulweb.domain.Article;
import smvulweb.vulweb.domain.Comment;
import smvulweb.vulweb.domain.member.Authority;
import smvulweb.vulweb.domain.member.Member;

@Getter
@Setter
public class AddMemberRequest {
    private String username;
    private String password;
    private String name;
    private Authority authority;

}
