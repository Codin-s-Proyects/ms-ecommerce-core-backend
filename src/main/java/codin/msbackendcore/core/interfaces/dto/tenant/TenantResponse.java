package codin.msbackendcore.core.interfaces.dto.tenant;

import java.util.UUID;

public record TenantResponse(
        UUID id,
        String slug,
        String name,
        String plan
) {}