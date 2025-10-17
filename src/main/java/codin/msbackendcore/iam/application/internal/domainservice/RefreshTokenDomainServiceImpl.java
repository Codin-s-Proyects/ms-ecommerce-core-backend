package codin.msbackendcore.iam.application.internal.domainservice;

import codin.msbackendcore.iam.application.internal.outboundservices.hashing.HashingService;
import codin.msbackendcore.iam.application.internal.outboundservices.token.TokenService;
import codin.msbackendcore.iam.domain.model.entities.RefreshToken;
import codin.msbackendcore.iam.domain.model.entities.User;
import codin.msbackendcore.iam.domain.services.RefreshTokenDomainService;
import codin.msbackendcore.iam.infrastructure.persistence.jpa.RefreshTokenRepository;
import codin.msbackendcore.shared.domain.exceptions.AuthenticatedException;
import codin.msbackendcore.shared.infrastructure.utils.Constants;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static codin.msbackendcore.shared.infrastructure.utils.Constants.REFRESH_TOKEN_EXPIRES_AT;

@Service
public class RefreshTokenDomainServiceImpl implements RefreshTokenDomainService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;

    public RefreshTokenDomainServiceImpl(RefreshTokenRepository refreshTokenRepository, HashingService hashingService, TokenService tokenService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
    }

    @Override
    public RefreshToken createRefreshToken(UUID tenantId, User user, String deviceInfo, String identifier) {

        String refreshAccessToken = tokenService.generateRefreshToken(identifier);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setTenantId(tenantId);
        refreshToken.setUser(user);
        refreshToken.setTokenHash(hashingService.encode(refreshAccessToken));
        refreshToken.setExpiresAt(REFRESH_TOKEN_EXPIRES_AT);
        refreshToken.setDeviceInfo(deviceInfo);

        refreshTokenRepository.save(refreshToken);

        refreshToken.setPlainToken(refreshAccessToken);

        return refreshToken;
    }

    @Override
    public RefreshToken useRefreshToken(UUID userId, String plainRefreshToken, UUID tenantId, String deviceInfo) {
//        var tokens = refreshTokenRepository.findRefreshTokenByRevoked_FalseAndUser_Id(userId);
//
//        var validToken = tokens.stream()
//                .filter(t -> hashingService.matches(plainRefreshToken, t.getTokenHash()))
//                .findFirst()
//                .orElseThrow(() -> new AuthenticatedException("error.unauthorized.refreshToken", new String[]{}, "tokenHash"));
//
//        if (validToken.isRevoked() || validToken.getExpiresAt().isBefore(Instant.now())) {
//            validToken.setRevoked(true);
//            refreshTokenRepository.save(validToken);
//            throw new AuthenticatedException("error.unauthorized.refreshToken.expiration", new String[]{}, "expirationDate");
//        }
//
//        validToken.setRevoked(true);
//        refreshTokenRepository.save(validToken);
//
//        var newPlainToken = UUID.randomUUID().toString() + UUID.randomUUID();
//        createRefreshToken(
//                tenantId,
//                validToken.getUser(),
//                deviceInfo != null ? deviceInfo : validToken.getDeviceInfo(),
//                Constants.REFRESH_TOKEN_IDENTIFIER
//        );)
//
//        var saved = refreshTokenRepository.save(newToken);
//        saved.setPlainToken(newPlainToken); // solo para devolver al response (no se guarda)
//
//        return saved;
        return null;
    }
}