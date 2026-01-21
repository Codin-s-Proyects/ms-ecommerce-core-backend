package codin.msbackendcore.ordering.domain.model.commands.order;

import codin.msbackendcore.ordering.domain.model.commands.ordercustomer.CreateOrderCustomerCommand;
import codin.msbackendcore.ordering.domain.model.commands.orderitem.CreateOrderItemCommand;
import codin.msbackendcore.ordering.domain.model.commands.ordershippingaddress.CreateOrderShippingAddressCommand;

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
        CreateOrderCustomerCommand customer,
        CreateOrderShippingAddressCommand shippingAddress,
        List<CreateOrderItemCommand> items
) {
}
