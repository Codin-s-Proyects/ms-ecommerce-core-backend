package codin.msbackendcore.core.domain.services.mediaasset;

import codin.msbackendcore.core.domain.model.entities.MediaAsset;
import codin.msbackendcore.core.domain.model.valueobjects.EntityType;
import codin.msbackendcore.core.domain.model.valueobjects.MediaAssetUsage;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface MediaAssetDomainService {
    MediaAsset createMediaAsset(UUID tenantId, EntityType entityType, UUID entityId, String url, String publicId, Boolean isMain, Integer sortOrder, Map<String, Object> assetMeta, Map<String, Object> context, MediaAssetUsage usage, String aiContext);

    MediaAsset updateMediaAsset(UUID mediaAssetId, UUID tenantId, Boolean isMain, Integer sortOrder, Map<String, Object> assetMeta, Map<String, Object> context, String aiContext);

    MediaAsset getMediaAsset(UUID mediaAssetId, UUID tenantId);

    List<MediaAsset> getAllByEntityTypeAndEntityId(UUID tenantId, EntityType entityType, UUID entityId);

    List<MediaAsset> getAllByTenantIdAndUsage(UUID tenantId, MediaAssetUsage usage);

    void unsetMainAsset(UUID tenantId, String entityType, UUID entityId);

    void deleteMediaAsset(UUID tenantId, UUID mediaAssetId);
}

