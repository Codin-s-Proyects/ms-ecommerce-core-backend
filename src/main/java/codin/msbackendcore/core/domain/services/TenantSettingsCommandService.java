package codin.msbackendcore.core.domain.services;

import codin.msbackendcore.core.domain.model.entities.TenantSettings;

import java.util.UUID;

public interface TenantSettingsCommandService {
    TenantSettings updateImagePrompt(UUID tenantId, String imagePrompt);
    TenantSettings updateComposerPrompt(UUID tenantId, String composerPrompt);
}
