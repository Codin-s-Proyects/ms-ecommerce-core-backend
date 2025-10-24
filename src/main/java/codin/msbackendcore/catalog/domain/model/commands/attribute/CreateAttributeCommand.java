package codin.msbackendcore.catalog.domain.model.commands.attribute;

import java.util.UUID;

public record CreateAttributeCommand(
        UUID tenantId,
        String code,
        String name,
        String dataType
) {
}

