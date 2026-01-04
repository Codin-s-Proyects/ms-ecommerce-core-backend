package codin.msbackendcore.iam.infrastructure.persistence.jpa;

import codin.msbackendcore.iam.domain.model.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {
    Optional<Session> getSessionByDeviceIdAndRevokedIsFalse(String deviceId);
    long countByUser_IdAndRevokedFalse(UUID userId);
    Optional<Session> findByUser_IdAndId(UUID userId, UUID id);
}
