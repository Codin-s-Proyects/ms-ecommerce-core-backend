package codin.msbackendcore.search.interfaces.dto;

import codin.msbackendcore.search.application.internal.dto.SemanticSearchDto;

import java.util.Map;
import java.util.UUID;

public record SemanticSearchResponse(
        SemanticSearchResponse.SemanticSearchProduct product,
        SemanticSearchResponse.SemanticSearchProductVariant variant) {

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

        return new SemanticSearchResponse(product, variant);
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
}

