package codin.msbackendcore.catalog.interfaces.dto.product;

import codin.msbackendcore.catalog.application.internal.dto.ProductDetailDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record ProductDetailResponse(
        ProductResponse product,
        List<ProductDetailMediaAsset> mediaAssets
) {

    public static ProductDetailResponse fromDto(ProductDetailDto dto) {
        var product = new ProductResponse(
                dto.product().getId(),
                dto.product().getTenantId(),
                dto.product().getName(),
                dto.product().getSlug(),
                dto.product().getDescription(),
                dto.product().isHasVariants(),
                dto.product().getStatus().name()
        );

        var mediaAssets = dto.mediaAssets().stream()
                .map(mediaAssetDto -> new ProductDetailMediaAsset(
                        mediaAssetDto.id(),
                        mediaAssetDto.entityType(),
                        mediaAssetDto.entityId(),
                        mediaAssetDto.url(),
                        mediaAssetDto.publicId(),
                        mediaAssetDto.isMain(),
                        mediaAssetDto.sortOrder(),
                        mediaAssetDto.assetMeta(),
                        mediaAssetDto.context(),
                        mediaAssetDto.usage(),
                        mediaAssetDto.aiContext()
                ))
                .toList();


        return new ProductDetailResponse(product, mediaAssets);
    }

    private record ProductDetailMediaAsset(
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
}

