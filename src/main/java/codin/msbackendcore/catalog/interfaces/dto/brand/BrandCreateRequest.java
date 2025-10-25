package codin.msbackendcore.catalog.interfaces.dto.brand;

import codin.msbackendcore.catalog.domain.model.commands.brand.CreateBrandCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record BrandCreateRequest(
        @NotNull UUID tenantId,
        @NotBlank String name,
        @NotBlank String description,
        @NotBlank String logoUrl
) {
    public CreateBrandCommand toCommand() {
        return new CreateBrandCommand(
                this.tenantId,
                this.name,
                this.description,
                this.logoUrl
        );
    }
}
