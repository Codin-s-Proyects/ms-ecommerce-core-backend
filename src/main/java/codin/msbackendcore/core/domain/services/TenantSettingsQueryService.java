package codin.msbackendcore.core.domain.services;

import codin.msbackendcore.core.domain.model.entities.TenantSettings;
import codin.msbackendcore.core.domain.model.queries.tenant_settings.GetTenantSettingsByTenantIdQuery;

import java.util.Optional;
import java.util.UUID;

public interface TenantSettingsQueryService {
    Optional<TenantSettings> handle(GetTenantSettingsByTenantIdQuery query);
}
