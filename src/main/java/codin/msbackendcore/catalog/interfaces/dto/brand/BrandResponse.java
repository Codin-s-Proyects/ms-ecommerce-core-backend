package codin.msbackendcore.catalog.interfaces.dto.brand;

import java.util.UUID;

public record BrandResponse(
        UUID id,
        UUID tenantId,
        String name,
        String slug,
        String description,
        String logoUrl
) {
}
