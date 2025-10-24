package codin.msbackendcore.core.application.internal.queryservice;

import codin.msbackendcore.core.domain.model.entities.TenantSettings;
import codin.msbackendcore.core.domain.model.queries.tenantsettings.GetTenantSettingsByTenantIdQuery;
import codin.msbackendcore.core.domain.services.TenantSettingsDomainService;
import codin.msbackendcore.core.domain.services.TenantSettingsQueryService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TenantSettingsQueryServiceImpl implements TenantSettingsQueryService {
    private final TenantSettingsDomainService domainService;

    public TenantSettingsQueryServiceImpl(TenantSettingsDomainService domainService) {
        this.domainService = domainService;
    }

    public Optional<TenantSettings> handle(GetTenantSettingsByTenantIdQuery query) {
        return domainService.getByTenantId(query.tenantId());
    }
}