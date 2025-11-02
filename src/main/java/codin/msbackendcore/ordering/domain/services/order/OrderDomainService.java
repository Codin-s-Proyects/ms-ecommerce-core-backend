package codin.msbackendcore.ordering.domain.services.order;

import codin.msbackendcore.ordering.domain.model.entities.Order;

import java.math.BigDecimal;
import java.util.UUID;

public interface OrderDomainService {
    Order createOrder(UUID tenantId, UUID userId, String orderNumber, String currencyCode, BigDecimal subtotal, BigDecimal discountTotal, BigDecimal total, String notes);
    Order addItemsAndStatusHistoryToOrder(Order order);
}
