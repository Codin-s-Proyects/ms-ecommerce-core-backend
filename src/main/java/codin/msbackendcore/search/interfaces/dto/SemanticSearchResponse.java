package codin.msbackendcore.search.interfaces.dto;

import codin.msbackendcore.search.application.internal.dto.SemanticSearchDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record SemanticSearchResponse(
        SemanticSearchResponse.SemanticSearchProduct product,
        SemanticSearchResponse.SemanticSearchProductVariant variant,
        List<SemanticSearchResponse.SemanticSearchProductPrice> prices) {

    public static SemanticSearchResponse fromDto(SemanticSearchDto dto) {
        var product = new SemanticSearchProduct(
                dto.product().id(),
                dto.product().tenantId(),
                dto.product().name(),
                dto.product().slug(),
                dto.product().description(),
                dto.product().hasVariants()
        );

        var variant = new SemanticSearchProductVariant(
                dto.productVariant().id(),
                dto.productVariant().tenantId(),
                dto.productVariant().sku(),
                dto.productVariant().name(),
                dto.productVariant().attributes(),
                dto.productVariant().imageUrl()
        );

        var prices = dto.productPrices().stream()
                .map(priceDto -> new SemanticSearchProductPrice(
                        priceDto.id(),
                        priceDto.tenantId(),
                        priceDto.productVariantId(),
                        priceDto.priceListId(),
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

    private record SemanticSearchProduct(
            UUID id,
            UUID tenantId,
            String name,
            String slug,
            String description,
            boolean hasVariants
    ) {
    }

    private record SemanticSearchProductVariant(
            UUID id,
            UUID tenantId,
            String sku,
            String name,
            Map<String, Object> attributes,
            String imageUrl
    ) {
    }


    private record SemanticSearchProductPrice(
            UUID id,
            UUID tenantId,
            UUID productVariantId,
            UUID priceListId,
            BigDecimal discountPercent,
            BigDecimal finalPrice,
            BigDecimal basePrice,
            Integer minQuantity,
            Instant validFrom,
            Instant validTo
    ) {
    }
}

