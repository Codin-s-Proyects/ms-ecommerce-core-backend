package codin.msbackendcore.ordering.domain.model.commands.order;

import java.util.UUID;

public record UpdateOrderStatusCommand(
        UUID tenantId,
        UUID orderId,
        String status
) {
}
