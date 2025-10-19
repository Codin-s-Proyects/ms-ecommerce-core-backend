package codin.msbackendcore.core.domain.services;

import codin.msbackendcore.core.domain.model.commands.UpdatePromptCommand;
import codin.msbackendcore.core.domain.model.entities.TenantSettings;

import java.util.List;

public interface TenantSettingsCommandService {
    TenantSettings handle(List<UpdatePromptCommand> command);
}
