package codin.msbackendcore.catalog.interfaces.dto.category;

import java.util.UUID;

public record CategoryResponse(
        UUID id,
        UUID tenantId,
        UUID parentId,
        String name,
        String slug,
        String description
) {
}
