package codin.msbackendcore.iam.domain.model.commands;

import java.util.UUID;

public record RefreshTokenCommand(
        String refreshToken,
        UUID userId,
        UUID tenantId,
        String identifier,
        String password,
        String deviceInfo,
        String ip
) {
}
