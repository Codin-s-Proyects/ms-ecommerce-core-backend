package codin.msbackendcore.catalog.interfaces.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ProductSearchRequest(
        @NotNull UUID tenantId,
        @NotBlank String query,
        int limit
) {}
