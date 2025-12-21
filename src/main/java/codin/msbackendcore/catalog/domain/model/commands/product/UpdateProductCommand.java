package codin.msbackendcore.catalog.domain.model.commands.product;

import java.util.Map;
import java.util.UUID;

public record UpdateProductCommand(
        UUID productId,
        UUID tenantId,
        UUID brandId,
        String name,
        String description,
        Map<String, Object> meta
) {
}

