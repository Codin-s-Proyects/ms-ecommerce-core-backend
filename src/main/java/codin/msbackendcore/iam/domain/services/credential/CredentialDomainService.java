package codin.msbackendcore.iam.domain.services.credential;

import codin.msbackendcore.iam.domain.model.entities.Credential;

import java.util.Optional;

public interface CredentialDomainService {
    Credential findByIdentifier(String identifier);
    boolean existsByIdentifier(String identifier);
}
