package codin.msbackendcore.catalog.interfaces.dto.categoryattribute;

import java.util.UUID;

public record CategoryAttributeResponse(
        UUID id,
        UUID tenantId,
        UUID categoryId,
        UUID attributeId
) {
}
