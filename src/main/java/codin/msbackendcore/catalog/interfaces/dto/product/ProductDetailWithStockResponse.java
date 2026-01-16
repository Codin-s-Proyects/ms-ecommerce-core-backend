package codin.msbackendcore.catalog.interfaces.dto.product;

import codin.msbackendcore.catalog.application.internal.dto.MediaAssetDto;

import java.util.List;
import java.util.UUID;

public record ProductDetailWithStockResponse(
    UUID id,
    UUID tenantId,
    String name,
    String slug,
    String description,
    boolean hasVariants,
    String status,
    Integer stock,
    List<MediaAssetDto> mediaAsset
){}