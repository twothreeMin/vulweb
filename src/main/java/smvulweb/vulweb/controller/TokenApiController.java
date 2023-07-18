package smvulweb.vulweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import smvulweb.vulweb.dto.token.CreateAccessTokenRequest;
import smvulweb.vulweb.dto.token.CreateAccessTokenResponse;
import smvulweb.vulweb.service.TokenService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
    @RestController
    public class TokenApiController {
        private final TokenService tokenService;
    
        @PostMapping("/api/token")
        public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(
                @RequestBody CreateAccessTokenRequest request) {
            String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());
    
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CreateAccessTokenResponse(newAccessToken));
        }

        @GetMapping("/api/token/isAuthenticated")
        public ResponseEntity<Map<String, Boolean>> checkAuthentication(HttpServletRequest request) {
            String token = request.getHeader("Authorization").substring(7); // "Bearer " 제거
            boolean valid = tokenService.validToken(token);
            Map<String, Boolean> response = new HashMap<>();
            response.put("isAuthenticated", valid);

            return ResponseEntity.ok(response);
        }
    }
