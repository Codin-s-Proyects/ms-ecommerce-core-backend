package codin.msbackendcore.core.application.internal.commandservice;

import codin.msbackendcore.core.domain.model.entities.TenantSettings;
import codin.msbackendcore.core.domain.services.TenantSettingsCommandService;
import codin.msbackendcore.core.domain.services.TenantSettingsDomainService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TenantSettingsCommandServiceImpl implements TenantSettingsCommandService {
    private final TenantSettingsDomainService domainService;

    public TenantSettingsCommandServiceImpl(TenantSettingsDomainService domainService) {
        this.domainService = domainService;
    }

    @Override
    public TenantSettings updateImagePrompt(UUID tenantId, String imagePrompt) {
        return domainService.updateImagePrompt(tenantId, imagePrompt);
    }

    @Override
    public TenantSettings updateComposerPrompt(UUID tenantId, String composerPrompt) {
        return domainService.updateComposerPrompt(tenantId, composerPrompt);
    }
}
