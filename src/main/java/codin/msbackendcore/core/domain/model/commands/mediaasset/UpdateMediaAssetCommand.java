package codin.msbackendcore.core.domain.model.commands.mediaasset;

import java.util.Map;
import java.util.UUID;

public record UpdateMediaAssetCommand(
        UUID mediaAssetId, UUID tenantId, Boolean isMain,
        Integer sortOrder, Map<String, Object> assetMeta, Map<String, Object> context,
        String aiContext
) {
}
