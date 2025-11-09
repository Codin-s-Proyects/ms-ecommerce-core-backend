package codin.msbackendcore.core.domain.services.tenantsettings;

import codin.msbackendcore.core.domain.model.commands.tenantsettings.UpdatePromptCommand;
import codin.msbackendcore.core.domain.model.entities.TenantSettings;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TenantSettingsDomainService {
    Optional<TenantSettings> getByTenantId(UUID tenantId);
    TenantSettings updatePrompts(UUID tenantId, List<UpdatePromptCommand> commands);
}
