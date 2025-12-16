package codin.msbackendcore.core.domain.services.tenant;

import codin.msbackendcore.core.application.internal.dto.TenantWithAssets;
import codin.msbackendcore.core.domain.model.queries.tenant.GetAllTenantsQuery;
import codin.msbackendcore.core.domain.model.queries.tenant.GetTenantByIdQuery;

import java.util.List;

public interface TenantQueryService {
    List<TenantWithAssets> handle(GetAllTenantsQuery query);
    TenantWithAssets handle(GetTenantByIdQuery query);
}
