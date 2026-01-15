package codin.msbackendcore.catalog.application.internal.dto;

import codin.msbackendcore.catalog.domain.model.entities.Product;

import java.util.List;

public record ProductDetailDto(
        Product product,
        List<MediaAssetDto> mediaAssets
) {
}
