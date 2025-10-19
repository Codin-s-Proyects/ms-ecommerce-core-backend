package codin.msbackendcore.core.interfaces.dto;

import java.util.UUID;

public record TenantResponse(
        UUID id,
        String slug,
        String name,
        String plan
) {}