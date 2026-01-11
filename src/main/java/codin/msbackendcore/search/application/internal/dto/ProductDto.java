package codin.msbackendcore.search.application.internal.dto;

import java.util.List;
import java.util.UUID;

public record ProductDto(
        UUID id,
        UUID tenantId,
        String name,
        String slug,
        String description,
        boolean hasVariants,
        List<MediaAssetDto> mediaAssets
) {
}
