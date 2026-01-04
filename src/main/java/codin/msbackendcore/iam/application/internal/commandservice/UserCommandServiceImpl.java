package codin.msbackendcore.iam.application.internal.commandservice;

import codin.msbackendcore.iam.application.internal.dto.SignInResult;
import codin.msbackendcore.iam.application.internal.outboundservices.acl.ExternalCoreService;
import codin.msbackendcore.iam.application.internal.outboundservices.token.TokenService;
import codin.msbackendcore.iam.domain.model.commands.LogoutCommand;
import codin.msbackendcore.iam.domain.model.commands.RefreshTokenCommand;
import codin.msbackendcore.iam.domain.model.commands.SignInCommand;
import codin.msbackendcore.iam.domain.model.commands.SignUpCommand;
import codin.msbackendcore.iam.domain.model.entities.User;
import codin.msbackendcore.iam.domain.model.valueobjects.CredentialType;
import codin.msbackendcore.iam.domain.model.valueobjects.UserType;
import codin.msbackendcore.iam.domain.services.AuditLogDomainService;
import codin.msbackendcore.iam.domain.services.RefreshTokenDomainService;
import codin.msbackendcore.iam.domain.services.SessionDomainService;
import codin.msbackendcore.iam.domain.services.credential.CredentialDomainService;
import codin.msbackendcore.iam.domain.services.role.RoleDomainService;
import codin.msbackendcore.iam.domain.services.user.UserCommandService;
import codin.msbackendcore.iam.domain.services.user.UserDomainService;
import codin.msbackendcore.shared.domain.exceptions.AuthenticatedException;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import codin.msbackendcore.shared.infrastructure.utils.CommonUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserCommandServiceImpl implements UserCommandService {

    private final UserDomainService userDomainService;
    private final CredentialDomainService credentialDomainService;
    private final RoleDomainService roleDomainService;

    private final AuditLogDomainService auditLogDomainService;
    private final SessionDomainService sessionDomainService;
    private final RefreshTokenDomainService refreshTokenDomainService;
    private final TokenService tokenService;

    private final ExternalCoreService externalCoreService;

    public UserCommandServiceImpl(
            CredentialDomainService credentialDomainService,
            UserDomainService userDomainService, RoleDomainService roleDomainService, AuditLogDomainService auditLogDomainService,
            SessionDomainService sessionDomainService, RefreshTokenDomainService refreshTokenDomainService, TokenService tokenService,
            ExternalCoreService externalCoreService
    ) {
        this.credentialDomainService = credentialDomainService;
        this.userDomainService = userDomainService;
        this.roleDomainService = roleDomainService;
        this.auditLogDomainService = auditLogDomainService;
        this.sessionDomainService = sessionDomainService;
        this.refreshTokenDomainService = refreshTokenDomainService;
        this.tokenService = tokenService;
        this.externalCoreService = externalCoreService;
    }

    @Override
    public Optional<SignInResult> handle(SignInCommand command) {
        var existedSession = sessionDomainService.findByDeviceId(command.deviceId());

        if (existedSession != null) {
            sessionDomainService.revokeSession(existedSession);
            refreshTokenDomainService.revokeAllTokensBySession(existedSession);
        }

        var credential = credentialDomainService.findByIdentifier(command.identifier());

        var user = command.tenantId() != null
                ? userDomainService.getUserByIdAndTenantId(credential.getUser().getId(), command.tenantId())
                : userDomainService.getUserById(credential.getUser().getId());

        if (!userDomainService.isPasswordValid(credential, command.password())) {
            throw new BadRequestException("error.authorization", new String[]{command.password()}, "password");
        }

        var session = sessionDomainService.createSession(user, command.ip(), command.deviceInfo(), command.deviceId());

        var refreshToken = refreshTokenDomainService.createRefreshToken(command.identifier(), session);

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

    @Override
    public Optional<User> handle(SignUpCommand command) {

        if (command.tenantId() != null && !externalCoreService.existTenantById(command.tenantId())) {
            throw new BadRequestException("error.bad_request", new String[]{command.tenantId().toString()}, "tenantId");
        }

        if (!CommonUtils.isValidEnum(CredentialType.class, command.type())) {
            throw new BadRequestException("error.bad_request", new String[]{command.type()}, "type");
        }

        if (!CommonUtils.isValidEnum(UserType.class, command.userType())) {
            throw new BadRequestException("error.bad_request", new String[]{command.type()}, "userType");
        }

        if (credentialDomainService.existsByIdentifier(command.identifier())) {
            throw new BadRequestException("error.already_exist", new String[]{command.identifier()}, "identifier");
        }

        var role = roleDomainService.findByRole(command.role());

        UUID systemUserId = userDomainService.findSystemUserId();

        User user = userDomainService.registerNewUser(command, systemUserId, role);


//        auditLogDomainService.recordAction(
//                user.getTenantId(),
//                systemUserId,
//                user.getId(),
//                "USER_SIGNUP",
//                Map.of(
//                        "type", command.type(),
//                        "identifier", command.identifier()
//                )
//        );

        return Optional.of(user);
    }

    @Override
    public Optional<SignInResult> handle(RefreshTokenCommand command) {

        var session = sessionDomainService.findByIdAndUserId(command.sessionId(), command.userId());

        if (session.isRevoked())
            throw new AuthenticatedException("error.session.revoked", new String[]{}, "sessionId");

        var refreshResult = refreshTokenDomainService.useRefreshToken(
                command.identifier(),
                command.refreshToken(),
                session
        );

        var accessToken = tokenService.generateToken(command.identifier());

        return Optional.of(
                new SignInResult(
                        command.userId(),
                        command.userType(),
                        accessToken,
                        refreshResult.getPlainToken(),
                        session.getId()
                )
        );
    }

    @Override
    public Optional<Void> handle(LogoutCommand command) {

        var session = sessionDomainService.findById(command.sessionId());

        if (session.isRevoked()) {
            throw new BadRequestException("session.revoked", new String[]{command.sessionId().toString()}, "sessionId");
        }

        sessionDomainService.revokeSession(session);
        refreshTokenDomainService.revokeAllTokensBySession(session);

//        auditLogDomainService.recordAction(
//                session.getUser().getTenantId(),
//                command.userId(),
//                command.userId(),
//                "USER_LOGOUT",
//                Map.of(
//                        "sessionId", command.sessionId().toString()
//                )
//        );

        return Optional.empty();
    }

}
