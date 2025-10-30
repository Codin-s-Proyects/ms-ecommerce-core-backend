package codin.msbackendcore.catalog.domain.model.queries.product;

import java.util.UUID;

public record GetAllProductByBrandAndTenantIdQuery(UUID brandId, UUID tenantId) {
}
