package codin.msbackendcore.ordering.interfaces.dto.order.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        UUID tenantId,
        UUID userId,
        String orderNumber,
        String status,
        String currencyCode,
        BigDecimal subtotal,
        BigDecimal discountTotal,
        BigDecimal total,
        String notes,
        String orderChannel,
         UUID trackingToken,
        OrderCustomerResponse customer,
        OrderShippingAddressResponse shippingAddress,
        Instant createdAt
) {
}
