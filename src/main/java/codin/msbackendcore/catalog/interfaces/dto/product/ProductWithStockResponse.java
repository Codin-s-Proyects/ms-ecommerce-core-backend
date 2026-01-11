package codin.msbackendcore.catalog.interfaces.dto.product;

import java.util.UUID;

public record ProductWithStockResponse(
    UUID id,
    UUID tenantId,
    String name,
    String slug,
    String description,
    boolean hasVariants,
    String status,
    Integer stock
){}