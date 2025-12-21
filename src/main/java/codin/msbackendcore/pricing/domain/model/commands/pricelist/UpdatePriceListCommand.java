package codin.msbackendcore.pricing.domain.model.commands.pricelist;

import java.time.Instant;
import java.util.UUID;

public record UpdatePriceListCommand(
        UUID priceListId,
        UUID tenantId,
        String name,
        String description,
        String currencyCode,
        Instant validFrom,
        Instant validTo
) {
}
