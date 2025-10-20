package codin.msbackendcore.catalog.interfaces.dto.attribute;

import codin.msbackendcore.catalog.domain.model.commands.attribute.CreateAttributeCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AttributeCreateRequest(
        @NotNull UUID tenantId,
        @NotBlank String code,
        @NotBlank String name,
        @NotBlank String dataType
) {
    public CreateAttributeCommand toCommand() {
        return new CreateAttributeCommand(
                tenantId,
                code,
                name,
                dataType
        );
    }
}
