package Group.Artifact.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import Group.Artifact.domain.entity.RefreshToken;
import Group.Artifact.domain.entity.User;
import Group.Artifact.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository; 
    private final UserService userService;

    public RefreshToken createRefreshToken(String token, Instant now, Instant expiry){
        RefreshToken refreshToken = RefreshToken.builder()
                            .expiryDate(expiry)
                            .token(token)
                            .revoked(false)
                            .user(null)
                            .build();
        return refreshToken;
    }

    public RefreshToken handleGetRefreshTokenByToken(String token){
        RefreshToken refreshToken = this.refreshTokenRepository.findByToken(token).
                                        orElseThrow(() -> new RuntimeException("this token is not existed"));
        this.handleVerifyRefreshToken(refreshToken);

        return refreshToken;
    }

    public void handleVerifyRefreshToken(RefreshToken refreshToken){
        if(refreshToken.isRevoked())throw new RuntimeException("refresh token is revoked");
        if(refreshToken.getExpiryDate().isBefore(Instant.now()))throw new RuntimeException("refresh token is expired");
    }

    public void handleAddUser(Long id, RefreshToken refreshToken){
        User userProxy = this.userService.handleGetUserProxyById(id);
        refreshToken.setUser(userProxy);
        this.refreshTokenRepository.save(refreshToken);
    }

    public void handleDeleteTokenByToken(RefreshToken refreshToken){
        this.refreshTokenRepository.delete(refreshToken);
    }

}
