package codin.msbackendcore.iam.domain.services;

import codin.msbackendcore.iam.domain.model.entities.Session;
import codin.msbackendcore.iam.domain.model.entities.User;

import java.util.UUID;

public interface SessionDomainService {
    Session findById(UUID sessionId);
    Session findByIdAndUserId(UUID sessionId, UUID userId);
    Session createSession(User user, String ipAddress, String deviceInfo);
    void revokeSession(Session session);
}
