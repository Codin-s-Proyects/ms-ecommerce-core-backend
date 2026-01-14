package codin.msbackendcore.ordering.domain.model.commands.order;

import codin.msbackendcore.ordering.domain.model.commands.orderitem.CreateOrderItemCommand;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateOrderCommand(
        UUID tenantId,
        UUID userId,
        String currencyCode,
        BigDecimal subtotal,
        BigDecimal discountTotal,
        BigDecimal total,
        String notes,
        String orderChannel,
        List<CreateOrderItemCommand> items
) {
}
