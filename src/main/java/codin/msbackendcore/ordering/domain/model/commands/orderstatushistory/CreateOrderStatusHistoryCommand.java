package codin.msbackendcore.ordering.domain.model.commands.orderstatushistory;

import java.util.UUID;

public record CreateOrderStatusHistoryCommand(
        UUID orderId,
        String status,
        UUID changedBy
) {
}
