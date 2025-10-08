package codin.msbackendcore.iam.infrastructure.authorization.sfs.services;

import codin.msbackendcore.iam.domain.model.entities.Credential;
import codin.msbackendcore.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import codin.msbackendcore.iam.infrastructure.persistence.jpa.CredentialRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("defaultUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CredentialRepository credentialRepository;

    public UserDetailsServiceImpl(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    // TODO: Colocar expeccion personalizada
    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        Credential credential = credentialRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with identifier: " + identifier
                ));

        return UserDetailsImpl.build(credential.getUser());
    }
}