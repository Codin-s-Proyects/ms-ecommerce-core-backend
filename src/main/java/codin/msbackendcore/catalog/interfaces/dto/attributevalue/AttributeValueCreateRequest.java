package codin.msbackendcore.catalog.interfaces.dto.attributevalue;

import codin.msbackendcore.catalog.domain.model.commands.attribute.CreateAttributeCommand;
import codin.msbackendcore.catalog.domain.model.commands.attributevalue.CreateAttributeValueCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AttributeValueCreateRequest(
        @NotNull UUID attributeId,
        @NotBlank String value,
        @NotBlank String label
) {
    public CreateAttributeValueCommand toCommand() {
        return new CreateAttributeValueCommand(
                attributeId,
                value,
                label
        );
    }
}
