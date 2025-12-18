package codin.msbackendcore.payments.application.internal.outboundservices;

import codin.msbackendcore.ordering.interfaces.acl.OrderingContextFacade;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExternalOrderingService {
    private final OrderingContextFacade orderingContextFacade;

    public ExternalOrderingService(OrderingContextFacade orderingContextFacade) {
        this.orderingContextFacade = orderingContextFacade;
    }

    public Integer getOrderCounterByTenant(UUID tenantId) {
        return orderingContextFacade.getOrderCounterByTenant(tenantId);
    }

    public boolean existOrderByIdAndTenantId(UUID orderId, UUID tenantId) {
        return orderingContextFacade.existOrderByIdAndTenantId(orderId, tenantId);
    }
}
