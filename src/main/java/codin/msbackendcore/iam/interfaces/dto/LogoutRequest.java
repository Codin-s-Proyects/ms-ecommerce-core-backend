package codin.msbackendcore.iam.interfaces.dto;

import codin.msbackendcore.iam.domain.model.commands.LogoutCommand;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record LogoutRequest(
        @NotNull UUID userId,
        @NotNull UUID sessionId,
        UUID tenantId
) {
    public LogoutCommand toCommand() {
        return new LogoutCommand(
                userId,
                sessionId,
                tenantId
        );
    }
}
