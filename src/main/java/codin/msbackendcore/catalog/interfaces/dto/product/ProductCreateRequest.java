package codin.msbackendcore.catalog.interfaces.dto.product;

import codin.msbackendcore.catalog.domain.model.commands.product.CreateProductCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;
import java.util.UUID;

public record ProductCreateRequest(
        @NotNull UUID tenantId,
        UUID brandId,
        @NotBlank String name,
        String description,
        Map<String, Object> meta
) {
    public CreateProductCommand toCommand() {
        return new CreateProductCommand(
                tenantId,
                brandId,
                name,
                description,
                meta
        );
    }
}
