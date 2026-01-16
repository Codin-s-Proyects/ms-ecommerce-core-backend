package codin.msbackendcore.catalog.interfaces.dto.productvariant;

import codin.msbackendcore.catalog.application.internal.dto.MediaAssetDto;
import codin.msbackendcore.catalog.application.internal.dto.ProductPriceDto;
import codin.msbackendcore.catalog.interfaces.dto.product.ProductResponse;
import codin.msbackendcore.catalog.interfaces.dto.product.ProductWithAssetsResponse;

import java.util.List;

public record ProductVariantDetailResponse(
        ProductWithAssetsResponse product,
        List<VariantDetailResponse> variants
) {
    public record VariantDetailResponse(
            ProductVariantResponse variant,
            List<MediaAssetDto> mediaAssets,
            List<ProductPriceDto> prices
    ) {
    }
}

