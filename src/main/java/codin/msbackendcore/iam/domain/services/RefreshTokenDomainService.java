package codin.msbackendcore.iam.domain.services;

import codin.msbackendcore.iam.domain.model.entities.RefreshToken;
import codin.msbackendcore.iam.domain.model.entities.Session;
import codin.msbackendcore.iam.domain.model.entities.User;

import java.util.UUID;

public interface RefreshTokenDomainService {
    RefreshToken createRefreshToken(String identifier, Session session);
    void revokeAllTokensBySession(Session session);
    RefreshToken useRefreshToken(String identifier, String plainRefreshToken, Session session);
}
