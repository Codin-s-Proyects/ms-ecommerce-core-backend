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
        Boolean isMain,
        Integer sortOrder,
        Map<String, Object> assetMeta,
        Map<String, Object> context,
        String usage,
        String aiContext
) {
}