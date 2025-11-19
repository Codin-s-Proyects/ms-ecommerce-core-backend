package codin.msbackendcore.catalog.domain.model.commands.brand;

import java.util.UUID;

public record CreateBrandCommand(
        UUID tenantId,
        String name,
        String description
) {
}
