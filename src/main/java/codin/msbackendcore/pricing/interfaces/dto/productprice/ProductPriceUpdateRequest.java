package codin.msbackendcore.pricing.interfaces.dto.productprice;

import codin.msbackendcore.pricing.domain.model.commands.productprice.UpdateProductPriceCommand;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductPriceUpdateRequest(
        UUID priceListId,
        BigDecimal basePrice,
        Integer minQuantity,
        Instant validTo
) {
    public UpdateProductPriceCommand toCommand(UUID tenantId, UUID productPriceId) {
        return new UpdateProductPriceCommand(
                productPriceId,
                tenantId,
                priceListId,
                basePrice,
                minQuantity,
                validTo
        );
    }
}
