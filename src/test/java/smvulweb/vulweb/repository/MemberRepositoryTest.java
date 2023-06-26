package smvulweb.vulweb.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import smvulweb.vulweb.domain.member.Member;
import smvulweb.vulweb.domain.member.Role;


import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    private void clear(){
        /** em.flush(), em.clear()란 하면 DB에 데이터를 반영하고, 영속성 컨텍스트를 지웁니다. **/
        em.flush();
        em.clear();
    }

    @AfterEach
    private void after(){
        em.clear();
    }

    @DisplayName("signup: 회원등록")
    @Test
    public void 회원가입_성공() throws Exception {
        //given
        Member saveMember = memberRepository.save(Member.builder()
                .username("username")
                .password("1234567890")
                .name("name1")
                .role(Role.USER)
                .build());

        //when
        Member findMember = memberRepository.findById(saveMember.getId())
                .orElseThrow(() -> new RuntimeException("저장된 회원이 없습니다"));
        //then
        assertThat(findMember).isSameAs(saveMember);
    }


    @DisplayName("error : 중복아이디 존재")
    @Test
    public void 에러_회원가입시_중복아이디_존재() throws Exception {
        //given
        Member member1 = Member.builder()
                .username("username")
                .password("1234567890")
                .name("Member1")
                .role(Role.USER)
                .build();
        Member member2 = Member.builder()
                .username("username")
                .password("1111111111")
                .name("Member2")
                .role(Role.USER)
                .build();

        memberRepository.save(member1);
        clear();

        //when, then
        assertThrows(Exception.class, () -> memberRepository.save(member2));

    }

    @DisplayName("error : 회원삭제")
    @Test
    public void 회원삭제_성공() throws Exception {
        //given
        Member member1 = Member.builder()
                .username("username")
                .password("1234567890")
                .name("Member1")
                .role(Role.USER)
                .build();
        memberRepository.save(member1);
        clear();

        //when
        memberRepository.delete(member1);
        clear();

        //then
        assertThrows(Exception.class,
                () -> memberRepository.findById(member1.getId())
                        .orElseThrow(() -> new Exception()));
    }


    @DisplayName("existByUsername_정상작동 확인")
    @Test
    public void existByUsername_정상작동() throws Exception {
        //given
        String username = "username";
        Member member1 = Member.builder()
                .username(username)
                .password("1234567890")
                .name("Member1").
                role(Role.USER)
                .name("name1")
                .build();
        memberRepository.save(member1);
        clear();

        //when, then
        assertThat(memberRepository.existsByUsername(username)).isTrue();
        assertThat(memberRepository.existsByUsername(username+"123")).isFalse();

    }


    @DisplayName("findByUsername_정상작동 확인")
    @Test
    public void findByUsername_정상작동() throws Exception {
        //given
        String username = "username";
        Member member1 = Member.builder()
                .username(username)
                .password("1234567890")
                .name("Member1")
                .role(Role.USER)
                .build();
        memberRepository.save(member1);
        clear();


        //when, then
        assertThat(memberRepository.findByUsername(username).get().getUsername()).isEqualTo(member1.getUsername());
        assertThat(memberRepository.findByUsername(username).get().getName()).isEqualTo(member1.getName());
        assertThat(memberRepository.findByUsername(username).get().getId()).isEqualTo(member1.getId());
        assertThrows(Exception.class,
                () -> memberRepository.findByUsername(username+"123")
                        .orElseThrow(() -> new Exception()));

    }

    @DisplayName("생성시간 등록 확인")
    @Test
    public void 회원가입시_생성시간_등록() throws Exception {
        //given
        Member member1 = Member.builder()
                .username("username")
                .password("1234567890")
                .name("Member1")
                .role(Role.USER)
                .build();
        memberRepository.save(member1);
        clear();

        //when
        Member findMember = memberRepository.findById(member1.getId()).orElseThrow(() -> new Exception());

        //then
        assertThat(findMember.getCreatedDate()).isNotNull();
        //assertThat(findMember.getLastModifiedDate()).isNotNull();
    }

    //    @Test
//    public void 성공_회원수정() throws Exception {
//        //given
//        Member member1 = Member.builder().username("username").password("1234567890").name("Member1").role(Role.USER).name("name1").age(22).build();
//        memberRepository.save(member1);
//        clear();
//
//        String updatePassword = "updatePassword";
//        String updateName = "updateName";
//        String updatename = "updatename";
//        int updateAge = 33;
//
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//        //when
//        Member findMember = memberRepository.findById(member1.getId()).orElseThrow(() -> new Exception());
//        findMember.updateAge(updateAge);
//        findMember.updateName(updateName);
//        findMember.updatename(updatename);
//        findMember.updatePassword(passwordEncoder,updatePassword);
//        em.flush();
//
//        //then
//        Member findUpdateMember = memberRepository.findById(findMember.getId()).orElseThrow(() -> new Exception());
//
//        assertThat(findUpdateMember).isSameAs(findMember);
//        assertThat(passwordEncoder.matches(updatePassword, findUpdateMember.getPassword())).isTrue();
//        assertThat(findUpdateMember.getName()).isEqualTo(updateName);
//        assertThat(findUpdateMember.getName()).isNotEqualTo(member1.getName());
//    }
}