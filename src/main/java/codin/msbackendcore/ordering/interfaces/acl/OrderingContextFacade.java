package codin.msbackendcore.ordering.interfaces.acl;

import codin.msbackendcore.ordering.domain.services.ordercounter.OrderCounterDomainService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderingContextFacade {

    private final OrderCounterDomainService orderCounterDomainService;

    public OrderingContextFacade(OrderCounterDomainService orderCounterDomainService) {
        this.orderCounterDomainService = orderCounterDomainService;
    }

    public Integer getOrderCounterByTenant(UUID tenantId) {
        return orderCounterDomainService.getOrderCounterByTenant(tenantId);
    }
}
