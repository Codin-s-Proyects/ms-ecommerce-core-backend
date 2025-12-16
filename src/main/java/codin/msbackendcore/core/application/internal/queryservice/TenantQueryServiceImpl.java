package codin.msbackendcore.core.application.internal.queryservice;

import codin.msbackendcore.core.application.internal.dto.TenantWithAssets;
import codin.msbackendcore.core.domain.model.queries.tenant.GetAllTenantsQuery;
import codin.msbackendcore.core.domain.model.queries.tenant.GetTenantByIdQuery;
import codin.msbackendcore.core.domain.model.valueobjects.EntityType;
import codin.msbackendcore.core.domain.services.mediaasset.MediaAssetDomainService;
import codin.msbackendcore.core.domain.services.tenant.TenantDomainService;
import codin.msbackendcore.core.domain.services.tenant.TenantQueryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantQueryServiceImpl implements TenantQueryService {

    private final TenantDomainService tenantDomainService;
    private final MediaAssetDomainService mediaAssetDomainService;

    public TenantQueryServiceImpl(TenantDomainService tenantDomainService, MediaAssetDomainService mediaAssetDomainService) {
        this.tenantDomainService = tenantDomainService;
        this.mediaAssetDomainService = mediaAssetDomainService;
    }

    @Override
    public List<TenantWithAssets> handle(GetAllTenantsQuery query) {

        var tenants = tenantDomainService.getAllTenants();

        return tenants.stream()
                .map(t -> {
                    var assets = mediaAssetDomainService.getAllByEntityTypeAndEntityId(
                            t.getId(),
                            EntityType.TENANT,
                            t.getId()
                    );
                    return new TenantWithAssets(t, assets);
                })
                .toList();
    }

    @Override
    public TenantWithAssets handle(GetTenantByIdQuery query) {
        var tenant = tenantDomainService.getTenantById(query.tenantId());
        var assets = mediaAssetDomainService.getAllByEntityTypeAndEntityId(
                tenant.getId(),
                EntityType.TENANT,
                tenant.getId()
        );

        return new TenantWithAssets(tenant, assets);

    }
}
