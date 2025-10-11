package codin.msbackendcore.iam.application.internal.domainservice;

import codin.msbackendcore.iam.domain.model.entities.Session;
import codin.msbackendcore.iam.domain.model.entities.User;
import codin.msbackendcore.iam.domain.services.SessionDomainService;
import codin.msbackendcore.iam.infrastructure.persistence.jpa.SessionRepository;
import org.springframework.stereotype.Service;

@Service
public class SessionDomainServiceImpl implements SessionDomainService {

    private final SessionRepository sessionRepository;

    public SessionDomainServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Session createSession(User user, String ipAddress, String deviceInfo) {
        Session session = new Session();
        session.setTenantId(user.getTenantId());
        session.setUser(user);
        session.setDeviceInfo(deviceInfo);
        session.setIp(ipAddress);
        session.setRevoked(false);
        return sessionRepository.save(session);
    }
}