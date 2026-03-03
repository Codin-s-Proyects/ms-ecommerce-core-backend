package codin.msbackendcore.ordering.interfaces.dto.order.response;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public record OrderItemResponse(
        UUID productVariantId,
        String productName,
        String sku,
        Map<String, Object> attributes,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal discountPercent,
        BigDecimal finalPrice,
        BigDecimal totalPrice
) {
}
