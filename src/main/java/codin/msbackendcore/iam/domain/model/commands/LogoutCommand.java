package codin.msbackendcore.iam.domain.model.commands;

import java.util.UUID;

public record LogoutCommand(
        UUID userId,
        UUID tenantId,
        String ip
) {
}
