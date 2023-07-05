package smvulweb.vulweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import smvulweb.vulweb.dto.auth.LoginRequest;
import smvulweb.vulweb.dto.token.CreateAccessTokenResponse;
import smvulweb.vulweb.service.AuthService;
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/api/auth/login")
    public ResponseEntity<CreateAccessTokenResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(new CreateAccessTokenResponse(token));
    }
}
