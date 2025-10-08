package codin.msbackendcore.iam.application.internal.commandservice;

import codin.msbackendcore.iam.application.internal.outboundservices.hashing.HashingService;
import codin.msbackendcore.iam.domain.model.commands.SignInCommand;
import codin.msbackendcore.iam.domain.model.commands.SignUpCommand;
import codin.msbackendcore.iam.domain.model.entities.Credential;
import codin.msbackendcore.iam.domain.model.entities.User;
import codin.msbackendcore.iam.domain.services.UserCommandService;
import codin.msbackendcore.iam.infrastructure.persistence.jpa.CredentialRepository;
import codin.msbackendcore.iam.infrastructure.persistence.jpa.UserRepository;
import codin.msbackendcore.shared.domain.exceptions.AuthenticatedException;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final HashingService hashingService;

    public UserCommandServiceImpl(
            UserRepository userRepository,
            CredentialRepository credentialRepository,
            HashingService hashingService
    ) {
        this.userRepository = userRepository;
        this.credentialRepository = credentialRepository;
        this.hashingService = hashingService;
    }

    @Override
    public Optional<User> handle(SignInCommand command) {
        var credential = credentialRepository.findByIdentifier(command.identifier())
                .orElseThrow(() ->
                        new NotFoundException("error.not_found", new String[]{command.identifier()}, "identifier")
                );

        if (!hashingService.matches(command.password(), credential.getSecretHash())) {
            throw new AuthenticatedException("error.authorization.password", new String[]{}, "password");
        }

        return Optional.of(credential.getUser());
    }

    @Transactional
    @Override
    public Optional<User> handle(SignUpCommand command) {
        User user = new User();
        user.setTenantId(command.tenantId());
        user.setUserType(command.userType());
        user.setActive(true);
        userRepository.save(user);

        Credential credential = new Credential();
        credential.setUser(user);
        credential.setType(command.userType());
        credential.setIdentifier(command.identifier());
        credential.setSecretHash(hashingService.encode(command.password()));
        credential.setPrimary(true);
        credentialRepository.save(credential);

        return Optional.of(user);
    }
}
