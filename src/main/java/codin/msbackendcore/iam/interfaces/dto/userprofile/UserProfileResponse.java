package codin.msbackendcore.iam.interfaces.dto.userprofile;

import java.time.Instant;
import java.util.UUID;

public record UserProfileResponse(
        UUID id,
        UUID userId,
        UUID tenantId,
        String firstName,
        String lastName,
        String email,
        String phone,
        String documentType,
        String documentNumber,
        Instant createdAt,
        Instant updatedAt
) {
}
