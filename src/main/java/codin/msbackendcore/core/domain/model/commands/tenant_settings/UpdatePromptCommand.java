package codin.msbackendcore.core.domain.model.commands.tenant_settings;

import java.util.UUID;

public record UpdatePromptCommand(
        UUID tenantId,
        String promptType,
        String prompt
) {
}
