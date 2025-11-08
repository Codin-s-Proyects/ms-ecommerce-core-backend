package codin.msbackendcore.catalog.interfaces.dto.productcategory;

import java.util.UUID;

public record ProductCategoryResponse(
        UUID id,
        UUID tenantId,
        UUID productId,
        UUID categoryId
) {
}