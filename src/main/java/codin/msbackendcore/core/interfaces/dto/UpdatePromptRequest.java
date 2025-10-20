package codin.msbackendcore.core.interfaces.dto;

import codin.msbackendcore.core.domain.model.commands.tenantsettings.UpdatePromptCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdatePromptRequest(
        @NotNull UUID tenantId,
        @NotNull String promptType,
        @NotBlank String value
) {
    public UpdatePromptCommand toCommand() {
        return new UpdatePromptCommand(
                tenantId,
                promptType,
                value
        );
    }
}
