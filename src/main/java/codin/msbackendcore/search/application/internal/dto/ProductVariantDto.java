package codin.msbackendcore.search.application.internal.dto;

import java.util.Map;
import java.util.UUID;

public record ProductVariantDto(
        UUID id,
        UUID productId,
        UUID tenantId,
        String sku,
        String name,
        Map<String, Object> attributes,
        Integer productQuantity
        ) {
}
