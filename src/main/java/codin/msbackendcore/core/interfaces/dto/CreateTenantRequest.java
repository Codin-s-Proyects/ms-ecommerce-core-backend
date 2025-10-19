package codin.msbackendcore.core.interfaces.dto;

import codin.msbackendcore.core.domain.model.commands.CreateTenantCommand;
import jakarta.validation.constraints.NotBlank;

public record CreateTenantRequest(
        @NotBlank String name
) {
    public CreateTenantCommand toCommand() {
        return new CreateTenantCommand(
                name
        );
    }
}
