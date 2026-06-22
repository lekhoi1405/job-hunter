package Group.Artifact.util;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Lettuce.Cluster.Refresh;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import Group.Artifact.domain.dto.login.UserLoginResponse;
import Group.Artifact.domain.entity.RefreshToken;
import Group.Artifact.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class SecurityUtil {

    public final JwtEncoder jwtEncoder;
    
    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;

    private RefreshTokenService refreshTokenService;

    @Value("${koiBong.jwt.base64-secret}")
    private String jwtKey;

    @Value("${koiBong.jwt.access-token-validity-in-seconds}")
    private long accessTokenExpiration;

    @Value("${koiBong.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;


    public String createAccessToken(Authentication authentication, UserLoginResponse userLoginResponse) {
        Instant now = Instant.now();
        Instant validity = now.plus(this.accessTokenExpiration, ChronoUnit.SECONDS);

        List<String> authorities = Arrays.asList("ROLE_USER_CREATE","ROLE_USER_UPDATE");
        userLoginResponse.setAuthorities(authorities);
        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuedAt(now)
            .expiresAt(validity)
            .subject(authentication.getName())
            .claim("user", userLoginResponse)
            .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader,claims)).getTokenValue();
    }

    public RefreshToken createRefreshToken(){
        String token = UUID.randomUUID().toString();
        Instant now = Instant.now();
        Instant expiry = now.plus(this.refreshTokenExpiration, ChronoUnit.SECONDS);
        return this.refreshTokenService.createRefreshToken(token, now, expiry);
    }

    public static Optional<String> getCurrentUser(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication){
        if(authentication == null){
            return null;
        }else if(authentication.getPrincipal() instanceof UserDetails springSecurityUser){
            return springSecurityUser.getUsername();
        }else if(authentication.getPrincipal() instanceof Jwt jwt){
            return jwt.getSubject();
        }else if(authentication.getPrincipal() instanceof String string){
            return string;
        }return null;
    }

    public static Optional<String> getCurrentUserJWt(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .filter(authentication -> authentication.getCredentials() instanceof String)
                .map(authentication -> (String)authentication.getCredentials());
    }
}

