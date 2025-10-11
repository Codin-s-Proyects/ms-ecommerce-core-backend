package codin.msbackendcore.iam.domain.services;

import codin.msbackendcore.iam.domain.model.entities.Session;
import codin.msbackendcore.iam.domain.model.entities.User;

public interface SessionDomainService {
    Session createSession(User user, String ipAddress, String deviceInfo);
}
