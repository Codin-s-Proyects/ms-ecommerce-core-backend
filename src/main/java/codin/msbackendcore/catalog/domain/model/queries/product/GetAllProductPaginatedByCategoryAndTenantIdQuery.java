package codin.msbackendcore.catalog.domain.model.queries.product;

import codin.msbackendcore.shared.infrastructure.pagination.model.CursorPaginationQuery;

import java.util.UUID;

public record GetAllProductPaginatedByCategoryAndTenantIdQuery(UUID categoryId, UUID tenantId, CursorPaginationQuery paginationQuery) {
}
