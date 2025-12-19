package codin.msbackendcore.iam.infrastructure.persistence.jpa;

import codin.msbackendcore.iam.domain.model.entities.RefreshToken;
import codin.msbackendcore.iam.domain.model.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    List<RefreshToken> getAllBySession(Session session);
    boolean existsByTokenHash(String tokenHash);
}
