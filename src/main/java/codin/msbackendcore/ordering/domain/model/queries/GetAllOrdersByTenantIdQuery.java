package codin.msbackendcore.ordering.domain.model.queries;

import java.util.UUID;

public record GetAllOrdersByTenantIdQuery(UUID tenantId) {
}
