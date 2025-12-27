package codin.msbackendcore.iam.application.internal.domainservice;

import codin.msbackendcore.iam.application.internal.outboundservices.hashing.HashingService;
import codin.msbackendcore.iam.application.internal.outboundservices.token.TokenService;
import codin.msbackendcore.iam.domain.model.entities.RefreshToken;
import codin.msbackendcore.iam.domain.model.entities.Session;
import codin.msbackendcore.iam.domain.model.entities.User;
import codin.msbackendcore.iam.domain.services.RefreshTokenDomainService;
import codin.msbackendcore.iam.infrastructure.persistence.jpa.RefreshTokenRepository;
import codin.msbackendcore.shared.domain.exceptions.AuthenticatedException;
import org.springframework.stereotype.Service;

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
    public RefreshToken createRefreshToken(String identifier, Session session) {

        String refreshAccessToken = tokenService.generateRefreshToken(identifier);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setTokenHash(hashingService.encode(refreshAccessToken));
        refreshToken.setExpiresAt(REFRESH_TOKEN_EXPIRES_AT);
        refreshToken.setSession(session);

        refreshTokenRepository.save(refreshToken);

        refreshToken.setPlainToken(refreshAccessToken);

        return refreshToken;
    }

    @Override
    public void revokeAllTokensBySession(Session session) {
        var refreshTokenList = refreshTokenRepository.getAllBySession(session);

        refreshTokenList.forEach(token -> token.setRevoked(true));

        refreshTokenRepository.saveAll(refreshTokenList);
    }

    @Override
    public RefreshToken useRefreshToken(String identifier, String plainRefreshToken, Session session) {

        var tokens = refreshTokenRepository.getAllBySession(session);

        RefreshToken token = tokens.stream()
                .filter(t -> hashingService.matches(plainRefreshToken, t.getTokenHash()))
                .findFirst()
                .orElseThrow(() -> new AuthenticatedException(
                        "error.unauthorized.refreshToken",
                        new String[]{},
                        "refreshToken"
                ));

        if (token.isRevoked()) {
            session.setRevoked(true);
            revokeAllTokensBySession(session);
            throw new AuthenticatedException(
                    "error.refreshToken.reuse",
                    new String[]{},
                    "refreshToken"
            );
        }

        if (token.getExpiresAt().isBefore(Instant.now())) {
            token.setRevoked(true);
            refreshTokenRepository.save(token);
            throw new AuthenticatedException(
                    "error.refreshToken.expired",
                    new String[]{},
                    "refreshToken"
            );
        }

        token.setRevoked(true);
        refreshTokenRepository.save(token);

        return createRefreshToken(
                identifier,
                session
        );
    }
}