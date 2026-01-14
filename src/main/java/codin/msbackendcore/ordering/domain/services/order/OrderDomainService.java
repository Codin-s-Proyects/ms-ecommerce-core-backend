package codin.msbackendcore.ordering.domain.services.order;

import codin.msbackendcore.ordering.domain.model.entities.Order;
import codin.msbackendcore.ordering.domain.model.valueobjects.OrderChannel;
import codin.msbackendcore.ordering.domain.model.valueobjects.OrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface OrderDomainService {
    Order createOrder(UUID tenantId, UUID userId, String orderNumber, String currencyCode, BigDecimal subtotal, BigDecimal discountTotal, BigDecimal total, OrderChannel orderChannel, String notes);
    Order updateOrderStatus(UUID tenantId, UUID orderId, OrderStatus orderStatus);
    Order persistOrder(Order order);
    Order getOrderById(UUID orderId, UUID tenantId);
    List<Order> getOrdersByTenantId(UUID tenantId);
    List<Order> getOrdersByUserId(UUID userId, UUID tenantId);
}
