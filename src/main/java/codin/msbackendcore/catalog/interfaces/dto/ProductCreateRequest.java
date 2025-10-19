package codin.msbackendcore.catalog.interfaces.dto;

import codin.msbackendcore.catalog.domain.model.commands.CreateProductCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;
import java.util.UUID;

public record ProductCreateRequest(
        @NotNull UUID tenantId,
        UUID categoryId,
        UUID brandId,
        @NotBlank String name,
        String description,
        Map<String, Object> meta
) {
    public CreateProductCommand toCommand() {
        return new CreateProductCommand(
                tenantId,
                categoryId,
                brandId,
                name,
                description,
                meta
        );
    }
}
