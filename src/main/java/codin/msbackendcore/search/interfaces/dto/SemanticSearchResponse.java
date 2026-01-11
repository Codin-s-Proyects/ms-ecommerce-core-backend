package codin.msbackendcore.search.interfaces.dto;

import codin.msbackendcore.search.application.internal.dto.PriceListDto;
import codin.msbackendcore.search.application.internal.dto.SemanticSearchDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record SemanticSearchResponse(
        SemanticSearchProduct product,
        SemanticSearchProductVariant variant,
        List<SemanticSearchProductPrice> prices) {

    public static SemanticSearchResponse fromDto(SemanticSearchDto dto) {

        var productMediaAssets = dto.product().mediaAssets().stream()
                .map(mediaAsset -> new SemanticSearchMediaAsset(
                        mediaAsset.id(),
                        mediaAsset.entityType(),
                        mediaAsset.entityId(),
                        mediaAsset.url(),
                        mediaAsset.publicId(),
                        mediaAsset.isMain(),
                        mediaAsset.sortOrder(),
                        mediaAsset.assetMeta(),
                        mediaAsset.context(),
                        mediaAsset.usage(),
                        mediaAsset.aiContext()
                )).toList();

        var product = new SemanticSearchProduct(
                dto.product().id(),
                dto.product().tenantId(),
                dto.product().name(),
                dto.product().slug(),
                dto.product().description(),
                dto.product().hasVariants(),
                productMediaAssets
        );

        var variantMediaAssets = dto.productVariant().mediaAssets().stream()
                .map(mediaAsset -> new SemanticSearchMediaAsset(
                        mediaAsset.id(),
                        mediaAsset.entityType(),
                        mediaAsset.entityId(),
                        mediaAsset.url(),
                        mediaAsset.publicId(),
                        mediaAsset.isMain(),
                        mediaAsset.sortOrder(),
                        mediaAsset.assetMeta(),
                        mediaAsset.context(),
                        mediaAsset.usage(),
                        mediaAsset.aiContext()
                )).toList();

        var variant = new SemanticSearchProductVariant(
                dto.productVariant().id(),
                dto.productVariant().tenantId(),
                dto.productVariant().sku(),
                dto.productVariant().name(),
                dto.productVariant().attributes(),
                dto.productVariant().productQuantity(),
                variantMediaAssets
        );

        var prices = dto.productPrices().stream()
                .map(priceDto -> new SemanticSearchProductPrice(
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

        return new SemanticSearchResponse(product, variant, prices);
    }

    private static SemanticSearchPriceList fromDto(PriceListDto dto) {
        return new SemanticSearchPriceList(
                dto.id(),
                dto.tenantId(),
                dto.code(),
                dto.name(),
                dto.description(),
                dto.currencyCode(),
                dto.validFrom(),
                dto.validTo(),
                dto.status()
        );
    }

    private record SemanticSearchProduct(
            UUID id,
            UUID tenantId,
            String name,
            String slug,
            String description,
            boolean hasVariants,
            List<SemanticSearchMediaAsset> mediaAssets
    ) {
    }

    private record SemanticSearchProductVariant(
            UUID id,
            UUID tenantId,
            String sku,
            String name,
            Map<String, Object> attributes,
            Integer productQuantity,
            List<SemanticSearchMediaAsset> mediaAssets
    ) {
    }

    private record SemanticSearchMediaAsset(
            UUID id,
            String entityType,
            UUID entityId,
            String url,
            String publicId,
            Boolean isMain,
            Integer sortOrder,
            Map<String, Object> assetMeta,
            Map<String, Object> context,
            String usage,
            String aiContext
    ) {
    }

    private record SemanticSearchProductPrice(
            UUID id,
            UUID tenantId,
            UUID productVariantId,
            SemanticSearchPriceList priceList,
            BigDecimal discountPercent,
            BigDecimal finalPrice,
            BigDecimal basePrice,
            Integer minQuantity,
            Instant validFrom,
            Instant validTo
    ) {
    }

    private record SemanticSearchPriceList(
            UUID id,
            UUID tenantId,
            String code,
            String name,
            String description,
            String currencyCode,
            Instant validFrom,
            Instant validTo,
            String status
    ) {
    }
}

