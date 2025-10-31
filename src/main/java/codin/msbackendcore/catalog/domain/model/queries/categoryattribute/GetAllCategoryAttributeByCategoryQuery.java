package codin.msbackendcore.catalog.domain.model.queries.categoryattribute;

import java.util.UUID;

public record GetAllCategoryAttributeByCategoryQuery(UUID tenantId, UUID categoryId) {
}
