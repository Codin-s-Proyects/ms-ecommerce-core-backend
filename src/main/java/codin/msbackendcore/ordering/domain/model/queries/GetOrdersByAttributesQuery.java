package codin.msbackendcore.ordering.domain.model.queries;

import java.util.UUID;

public record GetOrdersByAttributesQuery(
        String operation,
        UUID id,
        UUID tenantId,
        UUID userId,
        String orderNumber,
        String documentNumber,
        UUID trackingToken
) {
}
