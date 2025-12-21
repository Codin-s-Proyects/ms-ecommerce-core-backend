package codin.msbackendcore.core.domain.model.commands.mediaasset;

import java.util.Map;
import java.util.UUID;

public record UpdateMediaAssetCommand(
        UUID mediaAssetId, UUID tenantId,
        String url, String publicId, String format, Integer width,
        Integer height, Long bytes, Boolean isMain, Integer sortOrder, String altText, Map<String, Object> context
) {
}
