package codin.msbackendcore.core.application.internal.queryservice;

import codin.msbackendcore.core.domain.model.entities.Tenant;
import codin.msbackendcore.core.domain.model.queries.tenant.GetAllTenantsQuery;
import codin.msbackendcore.core.domain.services.tenant.TenantDomainService;
import codin.msbackendcore.core.domain.services.tenant.TenantQueryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantQueryServiceImpl implements TenantQueryService {

    private final TenantDomainService tenantDomainService;

    public TenantQueryServiceImpl(TenantDomainService tenantDomainService) {
        this.tenantDomainService = tenantDomainService;
    }

    @Override
    public List<Tenant> handle(GetAllTenantsQuery command) {
        return tenantDomainService.getAllTenants();
    }
}
