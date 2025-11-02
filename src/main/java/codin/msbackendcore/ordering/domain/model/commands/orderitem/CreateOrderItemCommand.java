package codin.msbackendcore.ordering.domain.model.commands.orderitem;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public record CreateOrderItemCommand(
        UUID productVariantId,
        String productName,
        String sku,
        Map<String, Object> attributes,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal discountPercent
) {
}
