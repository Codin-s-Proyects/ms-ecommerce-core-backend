package codin.msbackendcore.ordering.application.internal.queryservice;

import codin.msbackendcore.ordering.domain.model.entities.Order;
import codin.msbackendcore.ordering.domain.model.queries.GetAllOrdersByTenantIdQuery;
import codin.msbackendcore.ordering.domain.model.queries.GetAllOrdersByUserIdQuery;
import codin.msbackendcore.ordering.domain.model.queries.GetOrderByIdQuery;
import codin.msbackendcore.ordering.domain.services.order.OrderDomainService;
import codin.msbackendcore.ordering.domain.services.order.OrderQueryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderDomainService orderDomainService;

    public OrderQueryServiceImpl(OrderDomainService orderDomainService) {
        this.orderDomainService = orderDomainService;
    }

    @Override
    public Order handle(GetOrderByIdQuery query) {
        return orderDomainService.getOrderById(query.orderId(), query.tenantId());
    }

    @Override
    public List<Order> handle(GetAllOrdersByTenantIdQuery query) {
        return orderDomainService.getOrdersByTenantId(query.tenantId());
    }

    @Override
    public List<Order> handle(GetAllOrdersByUserIdQuery query) {
        return orderDomainService.getOrdersByUserId(query.userId(), query.tenantId());
    }
}
