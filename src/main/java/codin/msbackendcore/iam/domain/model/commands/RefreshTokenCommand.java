package codin.msbackendcore.iam.domain.model.commands;

import java.util.UUID;

public record RefreshTokenCommand(
        UUID userId,
        UUID sessionId,
        String refreshToken,
        String identifier,
        String userType
) {
}
