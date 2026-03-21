package codin.msbackendcore.iam.application.internal.commandservice;

import codin.msbackendcore.iam.application.internal.outboundservices.acl.ExternalCoreService;
import codin.msbackendcore.iam.domain.model.commands.CreateUserProfileCommand;
import codin.msbackendcore.iam.domain.model.entities.UserProfile;
import codin.msbackendcore.iam.domain.services.user.UserDomainService;
import codin.msbackendcore.iam.domain.services.userprofile.UserProfileCommandService;
import codin.msbackendcore.iam.domain.services.userprofile.UserProfileDomainService;
import codin.msbackendcore.ordering.domain.model.valueobjects.DocumentType;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.isValidEnum;

@Service
public class UserProfileCommandServiceImpl implements UserProfileCommandService {

    private final UserProfileDomainService userProfileDomainService;
    private final UserDomainService userDomainService;
    private final ExternalCoreService externalCoreService;

    public UserProfileCommandServiceImpl(UserProfileDomainService userProfileDomainService, UserDomainService userDomainService, ExternalCoreService externalCoreService) {
        this.userProfileDomainService = userProfileDomainService;
        this.userDomainService = userDomainService;
        this.externalCoreService = externalCoreService;
    }

    @Override
    public Optional<UserProfile> handle(CreateUserProfileCommand command) {

        if (!isValidEnum(DocumentType.class, command.documentType()))
            throw new BadRequestException("error.bad_request", new String[]{command.documentType()}, "documentType");

        if (command.tenantId() != null && !externalCoreService.existTenantById(command.tenantId())) {
            throw new BadRequestException("error.bad_request", new String[]{command.tenantId().toString()}, "tenantId");
        }

        var user = userDomainService.getUserById(command.userId());

        if (user == null)
            throw new BadRequestException("error.bad_request", new String[]{command.userId().toString()}, "userId");

        if (command.documentNumber() != null && userProfileDomainService.existsByDocumentNumber(command.documentNumber()))
            throw new BadRequestException("error.bad_request", new String[]{command.documentNumber()}, "documentNumber");

        var userProfile = userProfileDomainService.createUserProfile(
                user,
                command.tenantId(),
                command.firstName(),
                command.lastName(),
                command.email(),
                command.phone(),
                DocumentType.valueOf(command.documentType()),
                command.documentNumber()
        );

        return Optional.of(userProfile);
    }
}
