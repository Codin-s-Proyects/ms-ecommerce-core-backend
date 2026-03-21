package codin.msbackendcore.iam.interfaces.dto.userprofile;

import codin.msbackendcore.iam.domain.model.commands.CreateUserProfileCommand;

import java.util.UUID;

public record UserProfileCreateRequest(
        UUID userId,
        UUID tenantId,
        String firstName,
        String lastName,
        String email,
        String phone,
        String documentType,
        String documentNumber
) {
    public CreateUserProfileCommand toCommand() {

        return new CreateUserProfileCommand(
                userId,
                tenantId,
                firstName,
                lastName,
                email,
                phone,
                documentType,
                documentNumber
        );
    }
}
