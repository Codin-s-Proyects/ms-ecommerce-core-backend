package codin.msbackendcore.core.domain.services;

import codin.msbackendcore.core.domain.model.entities.TenantSettings;

import java.util.Optional;
import java.util.UUID;

public interface TenantSettingsQueryService {
    Optional<TenantSettings> getByTenantId(UUID tenantId);
}
