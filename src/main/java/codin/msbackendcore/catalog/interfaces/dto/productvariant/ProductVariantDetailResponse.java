package codin.msbackendcore.catalog.interfaces.dto.productvariant;

import codin.msbackendcore.catalog.application.internal.dto.PriceListDto;
import codin.msbackendcore.catalog.application.internal.dto.ProductVariantDetailDto;
import codin.msbackendcore.catalog.interfaces.dto.product.ProductResponse;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record ProductVariantDetailResponse(
        ProductResponse product,
        List<VariantDetailResponse> variants
) {

    public static ProductVariantDetailResponse fromDto(ProductVariantDetailDto dto) {
        var product = new ProductResponse(
                dto.product().getId(),
                dto.product().getTenantId(),
                dto.product().getName(),
                dto.product().getSlug(),
                dto.product().getDescription(),
                dto.product().isHasVariants()
        );

        var variants = dto.variants().stream()
                .map(variantDto -> {
                    var variant = new ProductVariantResponse(
                            variantDto.productVariant().getId(),
                            variantDto.productVariant().getProduct().getId(),
                            variantDto.productVariant().getTenantId(),
                            variantDto.productVariant().getSku(),
                            variantDto.productVariant().getName(),
                            variantDto.productVariant().getAttributes(),
                            variantDto.productVariant().getProductQuantity()
                    );

                    var mediaAssets = variantDto.mediaAssets().stream()
                            .map(mediaAssetDto -> new VariantDetailMediaAsset(
                                    mediaAssetDto.id(),
                                    mediaAssetDto.entityType(),
                                    mediaAssetDto.entityId(),
                                    mediaAssetDto.url(),
                                    mediaAssetDto.publicId(),
                                    mediaAssetDto.format(),
                                    mediaAssetDto.width(),
                                    mediaAssetDto.height(),
                                    mediaAssetDto.bytes(),
                                    mediaAssetDto.isMain(),
                                    mediaAssetDto.sortOrder(),
                                    mediaAssetDto.altText(),
                                    mediaAssetDto.context()
                            ))
                            .toList();

                    var prices = variantDto.productPrices().stream()
                            .map(priceDto -> new VariantDetailProductPrice(
                                    priceDto.id(),
                                    priceDto.tenantId(),
                                    priceDto.productVariantId(),
                                    fromDto(priceDto.priceList()),
                                    priceDto.discountPercent(),
                                    priceDto.finalPrice(),
                                    priceDto.basePrice(),
                                    priceDto.minQuantity(),
                                    priceDto.validFrom(),
                                    priceDto.validTo()
                            ))
                            .toList();

                    return new VariantDetailResponse(variant, mediaAssets, prices);
                })
                .toList();

        return new ProductVariantDetailResponse(product, variants);
    }

    private static VariantDetailMediaAssetPriceList fromDto(PriceListDto dto) {
        return new VariantDetailMediaAssetPriceList(
                dto.id(),
                dto.tenantId(),
                dto.code(),
                dto.name(),
                dto.description(),
                dto.currencyCode(),
                dto.validFrom(),
                dto.validTo(),
                dto.isActive()
        );
    }

    private record VariantDetailResponse(
            ProductVariantResponse variant,
            List<VariantDetailMediaAsset> mediaAssets,
            List<VariantDetailProductPrice> prices
    ) {
    }

    private record VariantDetailMediaAsset(
            UUID id,
            String entityType,
            UUID entityId,
            String url,
            String publicId,
            String format,
            Integer width,
            Integer height,
            Long bytes,
            Boolean isMain,
            Integer sortOrder,
            String altText,
            Map<String, Object> context
    ) {
    }

    private record VariantDetailProductPrice(
            UUID id,
            UUID tenantId,
            UUID productVariantId,
            VariantDetailMediaAssetPriceList priceList,
            BigDecimal discountPercent,
            BigDecimal finalPrice,
            BigDecimal basePrice,
            Integer minQuantity,
            Instant validFrom,
            Instant validTo
    ) {
    }

    private record VariantDetailMediaAssetPriceList(
            UUID id,
            UUID tenantId,
            String code,
            String name,
            String description,
            String currencyCode,
            Instant validFrom,
            Instant validTo,
            Boolean isActive
    ) {
    }
}

