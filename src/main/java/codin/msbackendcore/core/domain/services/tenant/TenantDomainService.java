package codin.msbackendcore.core.domain.services.tenant;

import codin.msbackendcore.core.domain.model.entities.Tenant;

import java.util.List;
import java.util.UUID;

public interface TenantDomainService {
    Tenant createTenant(String name);
    List<Tenant> getAllTenants();
    Tenant getTenantById(UUID tenantId);
}
