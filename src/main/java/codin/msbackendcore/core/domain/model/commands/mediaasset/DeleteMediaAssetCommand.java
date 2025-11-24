package codin.msbackendcore.core.domain.model.commands.mediaasset;

import java.util.UUID;

public record DeleteMediaAssetCommand(
        UUID mediaAssetId,
        UUID tenantId
) {
}
