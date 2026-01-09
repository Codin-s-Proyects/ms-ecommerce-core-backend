package codin.msbackendcore.core.application.internal.dto;

import java.util.Map;
import java.util.UUID;

public record CatalogEmbeddingDto(
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