package codin.msbackendcore.pricing.interfaces.dto.productprice;

import codin.msbackendcore.pricing.domain.model.commands.CreateProductPriceCommand;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductPriceCreateRequest(
        UUID tenantId,
        UUID productVariantId,
        UUID priceListId,
        BigDecimal basePrice,
        Integer minQuantity,
        Instant validTo
) {
    public CreateProductPriceCommand toCommand() {
        return new CreateProductPriceCommand(
                tenantId,
                productVariantId,
                priceListId,
                basePrice,
                minQuantity,
                validTo
        );
    }
}
