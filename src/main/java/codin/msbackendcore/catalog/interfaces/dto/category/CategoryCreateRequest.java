package codin.msbackendcore.catalog.interfaces.dto.category;

import codin.msbackendcore.catalog.domain.model.commands.category.CreateCategoryCommand;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CategoryCreateRequest(
        UUID tenantId,
        UUID parentId,
        @NotBlank String name) {
    public CreateCategoryCommand toCommand() {
        return new CreateCategoryCommand(
                this.tenantId,
                this.parentId,
                this.name
        );
    }
}
