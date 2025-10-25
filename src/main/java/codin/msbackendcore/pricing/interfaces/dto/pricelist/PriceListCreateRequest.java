package codin.msbackendcore.pricing.interfaces.dto.pricelist;

import codin.msbackendcore.pricing.domain.model.commands.CreatePriceListCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public record PriceListCreateRequest(
        @NotNull UUID tenantId,
        @NotBlank String code,
        @NotBlank String name,
        @NotBlank String description,
        @NotBlank String currencyCode,
        @NotNull Instant validFrom,
        @NotNull Instant validTo
) {

    public CreatePriceListCommand toCommand() {
        return new CreatePriceListCommand(
                tenantId,
                code,
                name,
                description,
                currencyCode,
                validFrom,
                validTo
        );
    }
}
