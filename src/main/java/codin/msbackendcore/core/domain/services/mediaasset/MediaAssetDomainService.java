package codin.msbackendcore.core.domain.services.mediaasset;

import codin.msbackendcore.core.domain.model.entities.MediaAsset;
import codin.msbackendcore.core.domain.model.valueobjects.EntityType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface MediaAssetDomainService {
    MediaAsset createMediaAsset(UUID tenantId, EntityType entityType, UUID entityId, String url, String publicId, String format, Integer width, Integer height, Long bytes, Boolean isMain, Integer sortOrder, String altText, Map<String, Object> context);
    MediaAsset updateMediaAsset(UUID mediaAssetId, UUID tenantId, String url, String publicId, String format, Integer width, Integer height, Long bytes, Boolean isMain, Integer sortOrder, String altText, Map<String, Object> context);
    List<MediaAsset> getAllByEntityTypeAndEntityId(UUID tenantId, EntityType entityType, UUID entityId);
    void deleteMediaAsset(UUID tenantId, UUID mediaAssetId);
}
