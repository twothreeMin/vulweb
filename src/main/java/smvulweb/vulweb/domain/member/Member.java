package smvulweb.vulweb.domain.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Member {
    /**
     * primary key(id)
     * username : 유저아이디
     * password : 비밀번호
     * name : 실명
     * role : 권한 (MANAGER, USER)
     **/

    @Id // id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "username", nullable = false, length = 30, unique = true) // 'title'이라는 not null 컬럼과 매핑
    private String username;

    @Column(name = "password", nullable = false, length = 30) // 'content'이라는 not null 컬럼과 매핑
    private String password;

    @Column(name = "name", nullable = false, length = 15)
    private String name;

    @Column(name = "created_date")
    @CreatedDate
    private LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    private Role role; //USER, MANAGER

    @Builder
    public Member(String username, String password, String name, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
    }


}
