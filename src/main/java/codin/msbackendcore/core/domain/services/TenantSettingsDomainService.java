package codin.msbackendcore.core.domain.services;

import codin.msbackendcore.core.domain.model.entities.TenantSettings;

import java.util.Optional;
import java.util.UUID;

public interface TenantSettingsDomainService {
    Optional<TenantSettings> getByTenantId(UUID tenantId);
    TenantSettings updateImagePrompt(UUID tenantId, String imagePrompt);
    TenantSettings updateComposerPrompt(UUID tenantId, String composerPrompt);
}
