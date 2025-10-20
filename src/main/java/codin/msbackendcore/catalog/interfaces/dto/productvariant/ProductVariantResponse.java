package codin.msbackendcore.catalog.interfaces.dto.productvariant;

import java.util.Map;
import java.util.UUID;

public record ProductVariantResponse(
    UUID id,
    UUID tenantId,
    String sku,
    String name,
    Map<String, Object> attributes,
    String imageUrl
){}