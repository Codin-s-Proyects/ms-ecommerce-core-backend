package codin.msbackendcore.iam.infrastructure.persistence.jpa;

import codin.msbackendcore.iam.domain.model.entities.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, UUID> {
    Optional<Credential> findByIdentifier(String identifier);
    boolean existsByIdentifier(String identifier);
}