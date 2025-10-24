package codin.msbackendcore.catalog.interfaces.dto.attribute;

import java.util.UUID;

public record AttributeResponse(
        UUID id,
        UUID tenantId,
        String code,
        String name,
        String dataType
) {
}