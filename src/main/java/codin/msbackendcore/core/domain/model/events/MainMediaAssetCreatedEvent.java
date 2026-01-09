package codin.msbackendcore.core.domain.model.events;

import java.util.UUID;

public record MainMediaAssetCreatedEvent(UUID tenantId, UUID entityId, String aiContext) {
}
