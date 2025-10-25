package codin.msbackendcore.catalog.interfaces.dto.category;

import codin.msbackendcore.catalog.domain.model.commands.category.CreateCategoryCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CategoryCreateRequest(
        @NotNull UUID tenantId,
        UUID parentId,
        @NotBlank String name,
        @NotBlank String description
) {
    public CreateCategoryCommand toCommand() {
        return new CreateCategoryCommand(
                this.tenantId,
                this.parentId,
                this.name,
                this.description
        );
    }
}
