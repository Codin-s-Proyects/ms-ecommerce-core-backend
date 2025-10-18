package codin.msbackendcore.catalog.interfaces.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record ProductCreateRequest(
        @NotNull UUID tenantId,
        @NotBlank String name,
        String description,
        List<VariantRequest> variants
) {
    public record VariantRequest(
            @NotBlank String sku,
            @NotBlank String name,
            String attributes // JSON string
    ) {}
}
