package codin.msbackendcore.iam.application.internal.commandservice;

import codin.msbackendcore.iam.domain.model.commands.SignInCommand;
import codin.msbackendcore.iam.domain.model.commands.SignUpCommand;
import codin.msbackendcore.iam.domain.model.entities.User;
import codin.msbackendcore.iam.domain.model.valueobjects.CredentialType;
import codin.msbackendcore.iam.domain.model.valueobjects.UserType;
import codin.msbackendcore.iam.domain.services.UserCommandService;
import codin.msbackendcore.iam.domain.services.UserDomainService;
import codin.msbackendcore.iam.infrastructure.persistence.jpa.CredentialRepository;
import codin.msbackendcore.iam.infrastructure.persistence.jpa.RoleRepository;
import codin.msbackendcore.iam.infrastructure.persistence.jpa.UserRepository;
import codin.msbackendcore.shared.domain.exceptions.AuthenticatedException;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import codin.msbackendcore.shared.infrastructure.utils.CommonUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final RoleRepository roleRepository;

    private final UserDomainService userDomainService;

    public UserCommandServiceImpl(
            UserRepository userRepository,
            CredentialRepository credentialRepository,
            UserDomainService userDomainService, RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.credentialRepository = credentialRepository;
        this.userDomainService = userDomainService;
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public Optional<User> handle(SignInCommand command) {
//        var credential = credentialRepository.findByIdentifier(command.identifier())
//                .orElseThrow(() ->
//                        new NotFoundException("error.not_found", new String[]{command.identifier()}, "identifier")
//                );
//
//        if (!command.hashingService().matches(command.password(), credential.getSecretHash())) {
//            throw new AuthenticatedException("error.authorization.password", new String[]{}, "password");
//        }
//
//        return Optional.of(credential.getUser());
        return Optional.of(new User());
    }

    @Transactional
    @Override
    public Optional<User> handle(SignUpCommand command) {

        if (!CommonUtils.isValidEnum(CredentialType.class, command.type())) {
            throw new BadRequestException("error.bad_request", new String[]{command.type()}, "type");
        }

        if (!CommonUtils.isValidEnum(UserType.class, command.userType())) {
            throw new BadRequestException("error.bad_request", new String[]{command.type()}, "userType");
        }

        if (credentialRepository.existsByIdentifier(command.identifier())) {
            throw new BadRequestException("error.already_exist", new String[]{command.identifier()}, "identifier");
        }

        if (roleRepository.existsByName(command.role())) {
            throw new BadRequestException("error.not_found", new String[]{command.role()}, "role");
        }

        User user = userDomainService.registerNewUser(command);

        userRepository.save(user);

        return Optional.of(user);
    }
}
