package codin.msbackendcore.core.domain.services;

import codin.msbackendcore.core.domain.model.entities.TenantSettings;
import codin.msbackendcore.core.domain.model.queries.tenantsettings.GetTenantSettingsByTenantIdQuery;

import java.util.Optional;

public interface TenantSettingsQueryService {
    Optional<TenantSettings> handle(GetTenantSettingsByTenantIdQuery query);
}
