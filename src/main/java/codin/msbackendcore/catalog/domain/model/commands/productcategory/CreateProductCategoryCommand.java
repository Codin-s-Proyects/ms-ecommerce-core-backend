package codin.msbackendcore.catalog.domain.model.commands.productcategory;

import java.util.UUID;

public record CreateProductCategoryCommand(
        UUID tenantId,
        UUID productId,
        UUID categoryId
) {
}

