package smvulweb.vulweb.domain.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Member implements UserDetails {
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

    @Column(name = "password", nullable = false, length = 80) // 'content'이라는 not null 컬럼과 매핑
    private String password;

    @Column(name = "name", nullable = false, length = 15)
    private String name;

    @Column(name = "created_date")
    @CreatedDate
    private LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    private Authority authority; //ROLE_USER, ROLE_MANAGER

    @Builder
    public Member(String username, String password, String name, Authority authority) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.authority = authority;
    }

    public void updatePassword(PasswordEncoder passwordEncoder, String password) {
        this.password = passwordEncoder.encode(password);
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + authority.name()));
    }

    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        //계정 만료 여부
        return true; // true -> 만료되지 않음
    }

    @Override
    public boolean isAccountNonLocked() {
        //계정 잠금 확인
        return true; //true -> 잠기지 않음
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //패스워드가 만료 여부 확인
        return true; // true -> 만료되지 않음
    }

    @Override
    public boolean isEnabled() {
        //계정이 사용 가능 여부 확인 로직
        return true; // true -> 사용가능
    }



}
