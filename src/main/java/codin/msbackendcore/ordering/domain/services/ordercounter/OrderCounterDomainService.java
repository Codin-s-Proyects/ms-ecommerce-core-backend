package codin.msbackendcore.ordering.domain.services.ordercounter;

import java.util.UUID;

public interface OrderCounterDomainService {
    int getOrderCounterByTenant(UUID tenantId);
}
