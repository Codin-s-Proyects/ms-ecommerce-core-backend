package codin.msbackendcore.iam.application.internal.domainservice;

import codin.msbackendcore.iam.domain.model.entities.Credential;
import codin.msbackendcore.iam.domain.services.credential.CredentialDomainService;
import codin.msbackendcore.iam.infrastructure.persistence.jpa.CredentialRepository;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CredentialDomainServiceImpl implements CredentialDomainService {

    private final CredentialRepository credentialRepository;

    public CredentialDomainServiceImpl(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    @Override
    public Credential findByIdentifier(String identifier) {
        return credentialRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new NotFoundException("error.not_found", new String[]{identifier}, "identifier"));
    }

    @Override
    public boolean existsByIdentifier(String identifier) {
        return credentialRepository.existsByIdentifier(identifier);
    }
}
