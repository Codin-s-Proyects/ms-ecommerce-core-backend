package codin.msbackendcore.core.domain.model.commands.mediaasset;

import java.util.Map;
import java.util.UUID;

public record CreateMediaAssetCommand(
        UUID tenantId, String entityType, UUID entityId, String url, String publicId, Boolean isMain,
        Integer sortOrder, Map<String, Object> assetMeta, Map<String, Object> context, String usage, String aiContext
) {
}
