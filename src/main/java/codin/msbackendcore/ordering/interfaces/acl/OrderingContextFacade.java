package codin.msbackendcore.ordering.interfaces.acl;

import codin.msbackendcore.ordering.domain.services.order.OrderDomainService;
import codin.msbackendcore.ordering.domain.services.ordercounter.OrderCounterDomainService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderingContextFacade {

    private final OrderCounterDomainService orderCounterDomainService;
    private final OrderDomainService orderDomainService;

    public OrderingContextFacade(OrderCounterDomainService orderCounterDomainService, OrderDomainService orderDomainService) {
        this.orderCounterDomainService = orderCounterDomainService;
        this.orderDomainService = orderDomainService;
    }

    public Integer getOrderCounterByTenant(UUID tenantId) {
        return orderCounterDomainService.getOrderCounterByTenant(tenantId);
    }

    public boolean existOrderByIdAndTenantId(UUID orderId, UUID tenantId) {
        var order = orderDomainService.getOrderById(orderId, tenantId);
        return order != null;
    }
}
