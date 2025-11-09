package codin.msbackendcore.core.domain.services.tenantsettings;

import codin.msbackendcore.core.domain.model.commands.tenantsettings.UpdatePromptCommand;
import codin.msbackendcore.core.domain.model.entities.TenantSettings;

import java.util.List;

public interface TenantSettingsCommandService {
    TenantSettings handle(List<UpdatePromptCommand> command);
}
