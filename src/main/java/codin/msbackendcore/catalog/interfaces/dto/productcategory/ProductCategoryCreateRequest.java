package codin.msbackendcore.catalog.interfaces.dto.productcategory;

import codin.msbackendcore.catalog.domain.model.commands.productcategory.CreateProductCategoryCommand;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record ProductCategoryCreateRequest(
        @NotNull UUID tenantId,
        Set<UUID> categoryIds
) {
    public CreateProductCategoryCommand toCommand(UUID productId) {
        return new CreateProductCategoryCommand(
                tenantId,
                productId,
                categoryIds
        );
    }
}
