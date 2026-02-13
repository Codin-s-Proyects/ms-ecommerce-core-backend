package codin.msbackendcore.catalog.domain.model.commands.productcategory;

import java.util.Set;
import java.util.UUID;

public record CreateProductCategoryCommand(
        UUID tenantId,
        UUID productId,
        Set<UUID> categoryIds
) {
}

