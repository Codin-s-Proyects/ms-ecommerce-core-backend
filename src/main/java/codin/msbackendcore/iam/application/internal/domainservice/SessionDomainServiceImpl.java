package codin.msbackendcore.iam.application.internal.domainservice;

import codin.msbackendcore.iam.domain.model.entities.Session;
import codin.msbackendcore.iam.domain.model.entities.User;
import codin.msbackendcore.iam.domain.services.SessionDomainService;
import codin.msbackendcore.iam.infrastructure.persistence.jpa.SessionRepository;
import codin.msbackendcore.shared.domain.exceptions.AuthenticatedException;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import static codin.msbackendcore.shared.infrastructure.utils.Constants.MAX_ACTIVE_SESSIONS;

@Service
public class SessionDomainServiceImpl implements SessionDomainService {

    private final SessionRepository sessionRepository;

    public SessionDomainServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Session createSession(User user, String ipAddress, String deviceInfo) {

        if (sessionRepository.existsByDeviceInfo(deviceInfo)) {
            throw new BadRequestException("error.already_exist", new String[]{deviceInfo}, "deviceInfo");
        }

        long activeSessions = sessionRepository.countByUser_IdAndRevokedFalse(user.getId());

        if (activeSessions >= MAX_ACTIVE_SESSIONS) {
            throw new AuthenticatedException("error.max_session_active", new String[]{}, "max_sessions");
        }

        Session session = new Session();
        session.setTenantId(user.getTenantId());
        session.setUser(user);
        session.setDeviceInfo(deviceInfo);
        session.setIp(ipAddress);
        session.setRevoked(false);
        return sessionRepository.save(session);
    }
}