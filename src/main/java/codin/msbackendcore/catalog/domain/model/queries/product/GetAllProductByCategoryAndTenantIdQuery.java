package codin.msbackendcore.catalog.domain.model.queries.product;

import java.util.UUID;

public record GetAllProductByCategoryAndTenantIdQuery(UUID categoryId, UUID tenantId) {
}
