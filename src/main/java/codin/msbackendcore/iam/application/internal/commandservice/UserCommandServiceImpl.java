package codin.msbackendcore.iam.application.internal.commandservice;

import codin.msbackendcore.iam.domain.model.commands.SignInCommand;
import codin.msbackendcore.iam.domain.model.commands.SignUpCommand;
import codin.msbackendcore.iam.domain.model.entities.User;
import codin.msbackendcore.iam.domain.model.valueobjects.CredentialType;
import codin.msbackendcore.iam.domain.model.valueobjects.UserType;
import codin.msbackendcore.iam.domain.services.AuditLogDomainService;
import codin.msbackendcore.iam.domain.services.UserCommandService;
import codin.msbackendcore.iam.domain.services.UserDomainService;
import codin.msbackendcore.iam.infrastructure.persistence.jpa.CredentialRepository;
import codin.msbackendcore.iam.infrastructure.persistence.jpa.RoleRepository;
import codin.msbackendcore.iam.infrastructure.persistence.jpa.UserRepository;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import codin.msbackendcore.shared.infrastructure.utils.CommonUtils;
import codin.msbackendcore.shared.infrastructure.utils.Constants;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final RoleRepository roleRepository;

    private final UserDomainService userDomainService;
    private final AuditLogDomainService auditLogDomainService;

    public UserCommandServiceImpl(
            UserRepository userRepository,
            CredentialRepository credentialRepository,
            UserDomainService userDomainService, RoleRepository roleRepository, AuditLogDomainService auditLogDomainService
    ) {
        this.userRepository = userRepository;
        this.credentialRepository = credentialRepository;
        this.userDomainService = userDomainService;
        this.roleRepository = roleRepository;
        this.auditLogDomainService = auditLogDomainService;
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

        //TODO: ADD TENANT VALIDATIONS

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

        auditLogDomainService.recordAction(
                user.getTenantId(),
                Constants.ADMIN_ASSIGNED,
                user.getId(),
                "USER_SIGNUP",
                Map.of(
                        "type", command.type(),
                        "identifier", command.identifier()
                )
        );

        return Optional.of(user);
    }
}
