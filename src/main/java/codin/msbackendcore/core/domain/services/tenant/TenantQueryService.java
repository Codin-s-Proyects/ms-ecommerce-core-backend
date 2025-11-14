package codin.msbackendcore.core.domain.services.tenant;

import codin.msbackendcore.core.domain.model.entities.Tenant;
import codin.msbackendcore.core.domain.model.queries.tenant.GetAllTenantsQuery;

import java.util.List;

public interface TenantQueryService {
    List<Tenant> handle(GetAllTenantsQuery command);
}
