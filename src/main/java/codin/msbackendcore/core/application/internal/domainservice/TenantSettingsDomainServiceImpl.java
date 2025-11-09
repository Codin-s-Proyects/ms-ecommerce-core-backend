package codin.msbackendcore.core.application.internal.domainservice;

import codin.msbackendcore.core.domain.model.commands.tenantsettings.UpdatePromptCommand;
import codin.msbackendcore.core.domain.model.entities.TenantSettings;
import codin.msbackendcore.core.domain.services.tenantsettings.TenantSettingsDomainService;
import codin.msbackendcore.core.infrastructure.persistence.jpa.TenantSettingsRepository;
import codin.msbackendcore.iam.interfaces.valueobjects.PromptType;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.isValidEnum;

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
    public TenantSettings updatePrompts(UUID tenantId, List<UpdatePromptCommand> commands) {
        var settings = repository.findByTenantId(tenantId)
                .orElseThrow(() -> new NotFoundException("error.not_found", new String[]{tenantId.toString()}, "tenantId"));

        for (var prompt : commands) {

            if (!isValidEnum(PromptType.class, prompt.promptType())) {
                throw new BadRequestException("error.bad_request", new String[]{prompt.promptType()}, "promptType");
            }

            var enumPromptType = PromptType.valueOf(prompt.promptType());

            switch (enumPromptType) {
                case IMAGE_PROMPT -> settings.setImagePrompt(prompt.prompt());
                case COMPOSER_PROMPT -> settings.setComposerPrompt(prompt.prompt());
            }
        }

        return repository.save(settings);
    }
}
