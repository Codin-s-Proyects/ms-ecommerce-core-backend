package codin.msbackendcore.core.domain.model.queries.mediaasset;

import java.util.UUID;

public record GetMediaAssetsByTenantAndUsageQuery(UUID tenantId, String usage) {
}
