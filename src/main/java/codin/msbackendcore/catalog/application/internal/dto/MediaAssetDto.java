package codin.msbackendcore.catalog.application.internal.dto;

import java.util.Map;
import java.util.UUID;

public record MediaAssetDto(
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
