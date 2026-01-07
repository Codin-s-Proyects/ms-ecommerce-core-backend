package codin.msbackendcore.catalog.application.internal.dto;

import java.util.Map;
import java.util.UUID;

public record MediaAssetDto(
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
