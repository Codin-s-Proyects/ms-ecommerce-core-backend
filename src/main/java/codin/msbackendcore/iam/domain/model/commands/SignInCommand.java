package codin.msbackendcore.iam.domain.model.commands;

import java.util.UUID;

public record SignInCommand(
        UUID tenantId,
        String identifier,
        String password,
        String deviceInfo,
        String ip
) {
}