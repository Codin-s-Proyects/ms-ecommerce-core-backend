package codin.msbackendcore.ordering.domain.services.orderitem;

import codin.msbackendcore.ordering.domain.model.entities.Order;
import codin.msbackendcore.ordering.domain.model.entities.OrderItem;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public interface OrderItemDomainService {
    OrderItem createOrderItem(UUID tenantId, Order order, UUID productVariantId, String productName, String sku, Map<String, Object> attributes, Integer quantity, BigDecimal unitPrice, BigDecimal discountPercent);
}
