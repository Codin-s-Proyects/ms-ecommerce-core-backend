package codin.msbackendcore.core.domain.services.tenant;

import codin.msbackendcore.core.application.internal.dto.TenantWithAssets;
import codin.msbackendcore.core.domain.model.queries.tenant.GetAllTenantsQuery;

import java.util.List;

public interface TenantQueryService {
    List<TenantWithAssets> handle(GetAllTenantsQuery command);
}
