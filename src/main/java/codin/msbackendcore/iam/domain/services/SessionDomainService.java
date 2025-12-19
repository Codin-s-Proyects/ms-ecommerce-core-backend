package codin.msbackendcore.iam.domain.services;

import codin.msbackendcore.iam.domain.model.entities.Session;
import codin.msbackendcore.iam.domain.model.entities.User;

import java.util.UUID;

public interface SessionDomainService {
    Session findById(UUID sessionId);
    Session createSession(User user, String ipAddress, String deviceInfo);
    void revokeSession(Session session);
}
