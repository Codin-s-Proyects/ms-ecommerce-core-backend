package codin.msbackendcore.search.application.internal.dto;

import java.util.List;

public record SemanticSearchDto(
        ProductDto product,
        ProductVariantDto productVariant,
        List<ProductPriceDto> productPrices
) {
}
