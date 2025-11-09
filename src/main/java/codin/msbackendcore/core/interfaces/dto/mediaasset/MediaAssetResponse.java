package codin.msbackendcore.core.interfaces.dto.mediaasset;

import java.util.Map;
import java.util.UUID;

public record MediaAssetResponse(
        UUID id,
        UUID tenantId,
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