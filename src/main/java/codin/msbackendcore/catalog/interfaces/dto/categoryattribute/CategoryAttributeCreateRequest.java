package codin.msbackendcore.catalog.interfaces.dto.categoryattribute;

import codin.msbackendcore.catalog.domain.model.commands.categoryattribute.CreateCategoryAttributeCommand;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CategoryAttributeCreateRequest(
        @NotNull UUID tenantId,
        @NotNull UUID categoryId,
        @NotNull UUID attributeId
) {
    public CreateCategoryAttributeCommand toCommand() {
        return new CreateCategoryAttributeCommand(
                this.tenantId,
                this.categoryId,
                this.attributeId
        );
    }
}
