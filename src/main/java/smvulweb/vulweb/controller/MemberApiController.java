package smvulweb.vulweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import smvulweb.vulweb.dto.member.AddMemberRequest;
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
        System.out.println("signout 호출!!       " + response);
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return ResponseEntity.ok().body(Collections.singletonMap("success", true));
        //        return ResponseEntity.ok().build();
    }
}
