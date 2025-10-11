package codin.msbackendcore.iam.application.internal.domainservice;

import codin.msbackendcore.iam.application.internal.outboundservices.hashing.HashingService;
import codin.msbackendcore.iam.application.internal.outboundservices.token.TokenService;
import codin.msbackendcore.iam.domain.model.entities.RefreshToken;
import codin.msbackendcore.iam.domain.model.entities.Session;
import codin.msbackendcore.iam.domain.model.entities.User;
import codin.msbackendcore.iam.domain.services.RefreshTokenDomainService;
import codin.msbackendcore.iam.domain.services.SessionDomainService;
import codin.msbackendcore.iam.infrastructure.persistence.jpa.RefreshTokenRepository;
import codin.msbackendcore.iam.infrastructure.persistence.jpa.SessionRepository;
import codin.msbackendcore.shared.infrastructure.utils.Constants;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

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
        refreshToken.setExpiresAt(Constants.REFRESH_TOKEN_EXPIRES_AT);
        refreshToken.setDeviceInfo(deviceInfo);

        refreshTokenRepository.save(refreshToken);

        refreshToken.setTokenHash(refreshAccessToken);

        return refreshToken;
    }
}