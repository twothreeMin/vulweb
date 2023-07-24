package smvulweb.vulweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import smvulweb.vulweb.domain.member.Member;
import smvulweb.vulweb.dto.member.AddMemberRequest;
import smvulweb.vulweb.dto.member.MemberResponse;
import smvulweb.vulweb.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/member")
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody AddMemberRequest request) {
        memberService.save(request);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return ResponseEntity.ok().body(Collections.singletonMap("success", true));
        //        return ResponseEntity.ok().build();
    }

    @GetMapping("/info")
    public ResponseEntity<?> getInfo(HttpServletRequest request, HttpServletResponse response) {
        // 현재 SecurityContext에서 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // authentication 객체에서 사용자의 정보를 얻어냄. 실제 유형은 설정에 따라 다름.
        Object principal = authentication.getPrincipal();

        // principal 객체가 Member 인스턴스인 경우
        if (principal instanceof User) {
            User Oauthuser = (User) principal;
            String email = Oauthuser.getUsername(); //user의 getUsername은 Email

            Member member = memberService.findByEmail(email);
            String picture = member.getPicture_url();

            // Member 객체를 MemberResponse 객체로 변환
            MemberResponse memberResponse = new MemberResponse(
                    member.getEmail(),
                    member.getNickname(),
                    member.getPicture_url(),
                    member.getAuthority());
            // 변환된 MemberResponse 객체를 반환
            return ResponseEntity.ok(memberResponse);
        } else {
            // 기타의 경우에 대한 처리. 여기서는 401 Unauthorized 상태 코드를 반환
            System.out.println("Principal is of type: " + principal.getClass().getName());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
