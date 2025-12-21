package codin.msbackendcore.pricing.interfaces.dto.pricelist;

import codin.msbackendcore.pricing.domain.model.commands.UpdatePriceListCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public record PriceListUpdatedRequest(
        @NotBlank String name,
        @NotBlank String description,
        @NotBlank String currencyCode,
        @NotNull Instant validFrom,
        @NotNull Instant validTo
) {

    public UpdatePriceListCommand toCommand(UUID priceListId, UUID tenantId) {
        return new UpdatePriceListCommand(
                priceListId,
                tenantId,
                name,
                description,
                currencyCode,
                validFrom,
                validTo
        );
    }
}
