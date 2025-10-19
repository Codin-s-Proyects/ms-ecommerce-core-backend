package codin.msbackendcore.core.application.internal.commandservice;

import codin.msbackendcore.core.domain.model.commands.UpdatePromptCommand;
import codin.msbackendcore.core.domain.model.entities.TenantSettings;
import codin.msbackendcore.core.domain.services.TenantSettingsCommandService;
import codin.msbackendcore.core.domain.services.TenantSettingsDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantSettingsCommandServiceImpl implements TenantSettingsCommandService {
    private final TenantSettingsDomainService domainService;

    public TenantSettingsCommandServiceImpl(TenantSettingsDomainService domainService) {
        this.domainService = domainService;
    }

    @Override
    public TenantSettings handle(List<UpdatePromptCommand> commands) {
        return domainService.updatePrompts(commands.getFirst().tenantId(), commands);
    }
}
