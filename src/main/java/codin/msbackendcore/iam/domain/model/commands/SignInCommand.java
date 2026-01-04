package codin.msbackendcore.iam.domain.model.commands;

import java.util.Map;
import java.util.UUID;

public record SignInCommand(
        UUID tenantId,
        String identifier,
        String password,
        String deviceId,
        Map<String, Object> deviceInfo,
        String ip
) {
}