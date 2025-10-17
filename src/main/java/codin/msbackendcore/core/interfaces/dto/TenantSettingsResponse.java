package codin.msbackendcore.core.interfaces.dto;

import java.time.Instant;
import java.util.UUID;

public record TenantSettingsResponse(
        UUID tenantId,
        String imagePrompt,
        String composerPrompt,
        Instant updatedAt
) {}