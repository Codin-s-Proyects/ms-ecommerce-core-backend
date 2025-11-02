package codin.msbackendcore.ordering.domain.services.orderstatushistory;

import codin.msbackendcore.ordering.domain.model.entities.Order;
import codin.msbackendcore.ordering.domain.model.entities.OrderStatusHistory;
import codin.msbackendcore.ordering.domain.model.valueobjects.OrderStatus;

import java.util.UUID;

public interface OrderStatusHistoryDomainService {
    OrderStatusHistory createOrderStatusHistory(Order order, OrderStatus orderStatus, UUID changedBy);
}
