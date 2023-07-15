package smvulweb.vulweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smvulweb.vulweb.domain.RefreshToken;
import smvulweb.vulweb.repository.RefreshTokenRepository;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken saveRefreshToken(Long userId, String refreshToken) {
        RefreshToken newToken = new RefreshToken(userId, refreshToken);
        return refreshTokenRepository.save(newToken);
    }

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }
}
