package codin.msbackendcore.iam.interfaces.dto;

import codin.msbackendcore.iam.domain.model.commands.LogoutCommand;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record LogoutRequest(
        @NotNull UUID userId,
        UUID tenantId,
        @Pattern(
                regexp = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$",
                message = "error.bad_request"
        )
        String ip
) {
    public LogoutCommand toCommand() {
        return new LogoutCommand(
                userId,
                tenantId,
                ip
        );
    }
}
