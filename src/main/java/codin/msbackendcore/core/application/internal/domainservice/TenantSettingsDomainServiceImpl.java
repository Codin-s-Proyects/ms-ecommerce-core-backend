package codin.msbackendcore.core.application.internal.domainservice;

import codin.msbackendcore.core.domain.model.entities.TenantSettings;
import codin.msbackendcore.core.domain.services.TenantSettingsDomainService;
import codin.msbackendcore.core.infrastructure.persistence.jpa.TenantSettingsRepository;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class TenantSettingsDomainServiceImpl implements TenantSettingsDomainService {

    private final TenantSettingsRepository repository;

    public TenantSettingsDomainServiceImpl(TenantSettingsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<TenantSettings> getByTenantId(UUID tenantId) {
        return repository.findByTenantId(tenantId);
    }

    @Transactional
    @Override
    public TenantSettings updateImagePrompt(UUID tenantId, String imagePrompt) {
        var settings = repository.findByTenantId(tenantId)
                .orElseThrow(() -> new NotFoundException("error.not_found", new String[]{tenantId.toString()}, "tenantId"));
        settings.setImagePrompt(imagePrompt);
        return repository.save(settings);
    }

    @Transactional
    @Override
    public TenantSettings updateComposerPrompt(UUID tenantId, String composerPrompt) {
        var settings = repository.findByTenantId(tenantId)
                .orElseThrow(() -> new NotFoundException("error.not_found", new String[]{tenantId.toString()}, "tenantId"));
        settings.setComposerPrompt(composerPrompt);
        return repository.save(settings);
    }
}
