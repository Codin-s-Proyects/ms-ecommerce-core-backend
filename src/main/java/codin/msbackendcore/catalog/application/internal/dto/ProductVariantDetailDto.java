package codin.msbackendcore.catalog.application.internal.dto;

import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.model.entities.ProductVariant;

import java.util.List;

public record ProductVariantDetailDto(
        Product product,
        List<VariantDetailDto> variants
) {
    public record VariantDetailDto(
            ProductVariant productVariant,
            List<MediaAssetDto> mediaAssets,
            List<ProductPriceDto> productPrices
    ) {
    }
}
