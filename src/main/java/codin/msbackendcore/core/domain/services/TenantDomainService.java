package codin.msbackendcore.core.domain.services;

import codin.msbackendcore.core.domain.model.entities.Tenant;

import java.util.UUID;

public interface TenantDomainService {
    Tenant createTenant(String name);
    Tenant getTenantById(UUID tenantId);
}
