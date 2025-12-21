package codin.msbackendcore.catalog.domain.model.commands.product;

import java.util.UUID;

public record DeleteProductCommand(
        UUID productId,
        UUID tenantId
) {
}

