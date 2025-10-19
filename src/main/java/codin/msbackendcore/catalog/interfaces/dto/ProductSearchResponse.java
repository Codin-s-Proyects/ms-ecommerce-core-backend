package codin.msbackendcore.catalog.interfaces.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductSearchResponse(
        UUID variantId,
        String variantName,
        String variantImageUrl,
        String sku,
        String productName,
        String productDescription,
        BigDecimal retailPrice,
        BigDecimal wholesalePrice
) {
}
