package codin.msbackendcore.iam.domain.services;

import codin.msbackendcore.iam.domain.model.entities.RefreshToken;
import codin.msbackendcore.iam.domain.model.entities.User;

import java.util.UUID;

public interface RefreshTokenDomainService {
    RefreshToken createRefreshToken(UUID tenantId, User user, String deviceInfo, String identifier);
    RefreshToken useRefreshToken(UUID userId, String plainRefreshToken, UUID tenantId, String deviceInfo);
}
