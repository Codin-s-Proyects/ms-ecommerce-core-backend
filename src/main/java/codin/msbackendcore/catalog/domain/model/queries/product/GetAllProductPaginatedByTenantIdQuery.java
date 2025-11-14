package codin.msbackendcore.catalog.domain.model.queries.product;

import codin.msbackendcore.shared.infrastructure.pagination.model.CursorPaginationQuery;

import java.util.UUID;

public record GetAllProductPaginatedByTenantIdQuery(UUID tenantId, CursorPaginationQuery paginationQuery) {
}
