package codin.msbackendcore.pricing.domain.model.commands;

import java.util.UUID;

public record DeletePriceListCommand(
        UUID priceListId,
        UUID tenantId
) {
}
