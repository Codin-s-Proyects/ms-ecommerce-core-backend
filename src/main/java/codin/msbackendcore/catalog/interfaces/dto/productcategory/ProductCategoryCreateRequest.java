package codin.msbackendcore.catalog.interfaces.dto.productcategory;

import codin.msbackendcore.catalog.domain.model.commands.productcategory.CreateProductCategoryCommand;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ProductCategoryCreateRequest(
        @NotNull UUID tenantId,
        @NotNull UUID productId,
        @NotNull UUID categoryId
) {
    public CreateProductCategoryCommand toCommand() {
        return new CreateProductCategoryCommand(
                tenantId,
                productId,
                categoryId
        );
    }
}
