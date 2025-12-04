package codin.msbackendcore.iam.application.internal.commandservice;

import codin.msbackendcore.iam.application.internal.dto.SignInResult;
import codin.msbackendcore.iam.application.internal.outboundservices.acl.ExternalCoreService;
import codin.msbackendcore.iam.application.internal.outboundservices.token.TokenService;
import codin.msbackendcore.iam.domain.model.commands.RefreshTokenCommand;
import codin.msbackendcore.iam.domain.model.commands.SignInCommand;
import codin.msbackendcore.iam.domain.model.commands.SignUpCommand;
import codin.msbackendcore.iam.domain.model.entities.User;
import codin.msbackendcore.iam.domain.model.valueobjects.CredentialType;
import codin.msbackendcore.iam.domain.model.valueobjects.UserType;
import codin.msbackendcore.iam.domain.services.*;
import codin.msbackendcore.iam.infrastructure.persistence.jpa.CredentialRepository;
import codin.msbackendcore.iam.infrastructure.persistence.jpa.RefreshTokenRepository;
import codin.msbackendcore.iam.infrastructure.persistence.jpa.RoleRepository;
import codin.msbackendcore.iam.infrastructure.persistence.jpa.UserRepository;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import codin.msbackendcore.shared.infrastructure.utils.CommonUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final UserDomainService userDomainService;
    private final AuditLogDomainService auditLogDomainService;
    private final SessionDomainService sessionDomainService;
    private final RefreshTokenDomainService refreshTokenDomainService;
    private final TokenService tokenService;

    private final ExternalCoreService externalCoreService;

    public UserCommandServiceImpl(
            UserRepository userRepository,
            CredentialRepository credentialRepository, RefreshTokenRepository refreshTokenRepository,
            UserDomainService userDomainService, RoleRepository roleRepository, AuditLogDomainService auditLogDomainService, SessionDomainService sessionDomainService, RefreshTokenDomainService refreshTokenDomainService, TokenService tokenService, ExternalCoreService externalCoreService
    ) {
        this.userRepository = userRepository;
        this.credentialRepository = credentialRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.userDomainService = userDomainService;
        this.roleRepository = roleRepository;
        this.auditLogDomainService = auditLogDomainService;
        this.sessionDomainService = sessionDomainService;
        this.refreshTokenDomainService = refreshTokenDomainService;
        this.tokenService = tokenService;
        this.externalCoreService = externalCoreService;
    }

    @Transactional
    @Override
    public Optional<SignInResult> handle(SignInCommand command) {
        var credential = credentialRepository.findByIdentifier(command.identifier())
                .orElseThrow(() -> new NotFoundException("error.not_found", new String[]{command.identifier()}, "identifier"));

        var user = userRepository.findByIdAndTenantId(credential.getUser().getId(), command.tenantId())
                .orElseThrow(() -> new NotFoundException("error.not_found", new String[]{command.tenantId().toString()}, "tenantId"));

        if (!userDomainService.isPasswordValid(credential, command.password())) {
            throw new BadRequestException("error.authorization", new String[]{command.password()}, "password");
        }

        var session = sessionDomainService.createSession(user, command.ip(), command.deviceInfo());

        var refreshToken = refreshTokenDomainService.createRefreshToken(command.tenantId(), user, command.deviceInfo(), command.identifier());

        var accessToken = tokenService.generateToken(command.identifier());

        userDomainService.updateUserLastLogin(user);

        return Optional.of(
                new SignInResult(
                        user.getId(),
                        user.getUserType(),
                        accessToken,
                        refreshToken.getPlainToken(),
                        session.getId()
                ));
    }

    @Transactional
    @Override
    public Optional<User> handle(SignUpCommand command) {

        if (!externalCoreService.existTenantById(command.tenantId())) {
            throw new BadRequestException("error.bad_request", new String[]{command.tenantId().toString()}, "tenantId");
        }

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

        UUID systemUserId = userRepository.findSystemUserIdByTenantId(command.tenantId())
                .orElseThrow(
                        () -> new BadRequestException("error.not_found", new String[]{command.tenantId().toString()}, "tenantId")
                );

        User user = userDomainService.registerNewUser(command, systemUserId);


        auditLogDomainService.recordAction(
                user.getTenantId(),
                systemUserId,
                user.getId(),
                "USER_SIGNUP",
                Map.of(
                        "type", command.type(),
                        "identifier", command.identifier()
                )
        );

        return Optional.of(user);
    }

    @Transactional
    @Override
    public Optional<SignInResult> handle(RefreshTokenCommand command) {
//        var newRefreshToken = refreshTokenDomainService.validateAndRotate(
//                command.userId(), command.refreshToken(), command.tenantId(), command.deviceInfo()
//        );
//
//        var identifier = newRefreshToken.getUser().getPrimaryCredential().getIdentifier();
//        var accessToken = tokenService.generateToken(identifier);
//
//        return new AuthResponse(
//                newRefreshToken.getUser().getId(),
//                accessToken,
//                newRefreshToken.getPlainToken(),
//                newRefreshToken.getUser().getUserType()
//        );

        return Optional.empty();
    }

}
