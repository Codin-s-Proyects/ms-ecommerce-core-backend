package codin.msbackendcore.catalog.interfaces.dto.productvariant;

import java.util.Map;
import java.util.UUID;

public record CatalogEmbeddingResponse(
        UUID tenantId,
        UUID variantId,
        String productName,
        String categoryName,
        String brandName,
        String productDescription,
        String variantName,
        Map<String, Object> variantAttributes
) {
}