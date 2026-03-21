package codin.msbackendcore.iam.domain.model.commands;

import java.util.UUID;

public record CreateUserProfileCommand(
        UUID userId,
        UUID tenantId,
        String firstName,
        String lastName,
        String email,
        String phone,
        String documentType,
        String documentNumber
) {
}
