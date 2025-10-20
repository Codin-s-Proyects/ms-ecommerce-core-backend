package codin.msbackendcore.core.domain.model.commands.tenantsettings;

import java.util.UUID;

public record UpdatePromptCommand(
        UUID tenantId,
        String promptType,
        String prompt
) {
}
