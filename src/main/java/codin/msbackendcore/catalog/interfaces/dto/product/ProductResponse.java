package codin.msbackendcore.catalog.interfaces.dto.product;

import java.util.UUID;

public record ProductResponse(
    UUID id,
    UUID tenantId,
    String name,
    String slug,
    String description,
    boolean hasVariants,
    String status
){}