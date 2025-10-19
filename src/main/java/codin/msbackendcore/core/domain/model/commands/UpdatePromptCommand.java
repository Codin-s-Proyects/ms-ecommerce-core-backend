package codin.msbackendcore.core.domain.model.commands;

import codin.msbackendcore.iam.interfaces.valueobjects.PromptType;

import java.util.UUID;

public record UpdatePromptCommand(
        UUID tenantId,
        String promptType,
        String prompt
) {
}
