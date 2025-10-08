package codin.msbackendcore.iam.domain.model.commands;

import java.util.UUID;

public record SignUpCommand(
        UUID tenantId,
        String userType,
        String identifier,
        String password,
        boolean isPrimary
) {}