package codin.msbackendcore.catalog.domain.model.queries.productvariant;

import java.util.UUID;

public record GetProductVariantByProductAndTenantIdQuery(UUID productId, UUID tenantId) {
}
