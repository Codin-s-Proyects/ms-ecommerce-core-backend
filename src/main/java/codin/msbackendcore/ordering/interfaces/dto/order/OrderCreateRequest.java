package codin.msbackendcore.ordering.interfaces.dto.order;

import codin.msbackendcore.ordering.domain.model.commands.order.CreateOrderCommand;
import codin.msbackendcore.ordering.interfaces.dto.orderitem.OrderItemCreateRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderCreateRequest(
        @NotNull UUID tenantId,
        @NotNull UUID userId,
        @NotBlank String currencyCode,
        @NotNull BigDecimal subtotal,
        @NotNull BigDecimal discountTotal,
        @NotNull BigDecimal total,
        String notes,
        @NotBlank String orderChannel,
        List<OrderItemCreateRequest> items
) {
    public CreateOrderCommand toCommand() {

        var itemsCommand = this.items.stream()
                .map(OrderItemCreateRequest::toCommand)
                .toList();

        return new CreateOrderCommand(
                tenantId,
                userId,
                currencyCode,
                subtotal,
                discountTotal,
                total,
                notes,
                orderChannel,
                itemsCommand
        );
    }
}
