package codin.msbackendcore.catalog.domain.model.queries.product;

import java.util.UUID;

public record GetProductByIdQuery(UUID productId, UUID tenantId) {
}
