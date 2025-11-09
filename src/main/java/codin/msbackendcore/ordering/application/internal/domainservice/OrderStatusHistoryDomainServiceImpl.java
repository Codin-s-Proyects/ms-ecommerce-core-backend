package codin.msbackendcore.ordering.application.internal.domainservice;

import codin.msbackendcore.ordering.domain.model.entities.Order;
import codin.msbackendcore.ordering.domain.model.entities.OrderStatusHistory;
import codin.msbackendcore.ordering.domain.model.valueobjects.OrderStatus;
import codin.msbackendcore.ordering.domain.services.orderstatushistory.OrderStatusHistoryDomainService;
import codin.msbackendcore.ordering.infrastructure.persistence.jpa.OrderStatusHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderStatusHistoryDomainServiceImpl implements OrderStatusHistoryDomainService {

    private final OrderStatusHistoryRepository orderStatusHistoryRepository;

    public OrderStatusHistoryDomainServiceImpl(OrderStatusHistoryRepository orderStatusHistoryRepository) {
        this.orderStatusHistoryRepository = orderStatusHistoryRepository;
    }

    @Override
    public OrderStatusHistory createOrderStatusHistory(Order order, OrderStatus orderStatus, UUID changedBy) {
        return OrderStatusHistory.builder()
                .order(order)
                .status(orderStatus)
                .changedBy(changedBy)
                .build();
    }
}
