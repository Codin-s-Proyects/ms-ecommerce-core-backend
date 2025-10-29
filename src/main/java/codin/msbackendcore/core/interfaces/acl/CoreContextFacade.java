package codin.msbackendcore.core.interfaces.acl;

import codin.msbackendcore.core.domain.services.TenantDomainService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CoreContextFacade {

    private final TenantDomainService tenantDomainService;

    public CoreContextFacade(TenantDomainService tenantDomainService) {
        this.tenantDomainService = tenantDomainService;
    }

    public UUID getTenantById(UUID tenantId) {
        var tenant = tenantDomainService.getTenantById(tenantId);
        return tenant.getId();
    }
}
