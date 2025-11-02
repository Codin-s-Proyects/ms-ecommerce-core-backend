package codin.msbackendcore.ordering.domain.model.queries;

import java.util.UUID;

public record GetAllOrdersByUserIdQuery(UUID tenantId, UUID userId) {
}
