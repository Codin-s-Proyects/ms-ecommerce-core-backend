package codin.msbackendcore.ordering.domain.model.queries;

import java.util.UUID;

public record GetOrderByIdQuery(UUID orderId, UUID tenantId) {
}
