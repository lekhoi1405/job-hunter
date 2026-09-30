package Group.Artifact.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import Group.Artifact.domain.entity.RefreshToken;
import Group.Artifact.domain.entity.User;
import Group.Artifact.repository.RefreshTokenRepository;
import Group.Artifact.util.error.ExceptionCustom.RefreshTokenInvalidException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository; 

    public RefreshToken createRefreshToken(String token, Instant now, Instant expiry){
        RefreshToken refreshToken = RefreshToken.builder()
                            .expiryDate(expiry)
                            .token(token)
                            .revoked(false)
                            .user(null)
                            .build();
        return refreshToken;
    }

    public RefreshToken handleGetRefreshTokenByTokenWithUser(String token){
        RefreshToken refreshToken = this.refreshTokenRepository.findByTokenWithUser(token).
                                        orElseThrow(RefreshTokenInvalidException::new);
        this.handleVerifyRefreshToken(refreshToken);

        return refreshToken;
    }

    public RefreshToken handleGetRefreshTokenByToken(String token){
        RefreshToken refreshToken = this.refreshTokenRepository.findByToken(token).
                                        orElseThrow(RefreshTokenInvalidException::new);
        this.handleVerifyRefreshToken(refreshToken);

        return refreshToken;
    }

    public void handleVerifyRefreshToken(RefreshToken refreshToken){
        if(refreshToken.isRevoked() || refreshToken.getExpiryDate().isBefore(Instant.now())){
            throw new RefreshTokenInvalidException();
        }
    }

    public void handleAddUser(User userId, RefreshToken refreshToken){
        refreshToken.setUser(userId);
        this.refreshTokenRepository.save(refreshToken);
    }

    public void handleDeleteTokenByToken(String token){
        this.refreshTokenRepository.deleteByToken(token);
    }

    public void handleDeleteByUserId(Long userId){
        this.refreshTokenRepository.deleteByUserId(userId);
    }

}
