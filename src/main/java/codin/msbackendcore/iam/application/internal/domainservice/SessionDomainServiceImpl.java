package codin.msbackendcore.iam.application.internal.domainservice;

import codin.msbackendcore.iam.domain.model.entities.Session;
import codin.msbackendcore.iam.domain.model.entities.User;
import codin.msbackendcore.iam.domain.services.SessionDomainService;
import codin.msbackendcore.iam.infrastructure.persistence.jpa.SessionRepository;
import codin.msbackendcore.shared.domain.exceptions.AuthenticatedException;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static codin.msbackendcore.shared.infrastructure.utils.Constants.MAX_ACTIVE_SESSIONS;

@Service
public class SessionDomainServiceImpl implements SessionDomainService {

    private final SessionRepository sessionRepository;

    public SessionDomainServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Session findById(UUID sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(
                        () -> new NotFoundException("error.not_found", new String[]{sessionId.toString()}, "sessionId")
                );
    }

    @Override
    public Session findByIdAndUserId(UUID sessionId, UUID userId) {
        return sessionRepository.findByUser_IdAndId(userId, sessionId)
                .orElseThrow(
                        () -> new NotFoundException("error.not_found", new String[]{sessionId.toString()}, "sessionId")
                );
    }

    @Override
    public Session createSession(User user, String ipAddress, String deviceInfo) {

        if (sessionRepository.existsByDeviceInfoAndRevokedIsFalse(deviceInfo)) {
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

    @Override
    public void revokeSession(Session session) {
        session.setRevoked(true);
        sessionRepository.save(session);
    }
}