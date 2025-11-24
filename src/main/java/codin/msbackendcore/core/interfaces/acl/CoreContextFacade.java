package codin.msbackendcore.core.interfaces.acl;

import codin.msbackendcore.core.domain.model.valueobjects.EntityType;
import codin.msbackendcore.core.domain.services.mediaasset.MediaAssetDomainService;
import codin.msbackendcore.core.domain.services.tenant.TenantDomainService;
import codin.msbackendcore.core.interfaces.dto.mediaasset.MediaAssetResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CoreContextFacade {

    private final TenantDomainService tenantDomainService;
    private final MediaAssetDomainService mediaAssetDomainService;

    public CoreContextFacade(TenantDomainService tenantDomainService, MediaAssetDomainService mediaAssetDomainService) {
        this.tenantDomainService = tenantDomainService;
        this.mediaAssetDomainService = mediaAssetDomainService;
    }

    public UUID getTenantById(UUID tenantId) {
        var tenant = tenantDomainService.getTenantById(tenantId);
        return tenant.getId();
    }

    public List<MediaAssetResponse> getMediaAssetByEntityIdAndEntityType(UUID tenantId, EntityType entityType, UUID entityId) {
        return mediaAssetDomainService.getAllByEntityTypeAndEntityId(tenantId, entityType, entityId)
                .stream()
                .map(mediaAsset -> new MediaAssetResponse(
                        mediaAsset.getId(),
                        mediaAsset.getTenantId(),
                        mediaAsset.getEntityType().toString(),
                        mediaAsset.getEntityId(),
                        mediaAsset.getUrl(),
                        mediaAsset.getPublicId(),
                        mediaAsset.getFormat(),
                        mediaAsset.getWidth(),
                        mediaAsset.getHeight(),
                        mediaAsset.getBytes(),
                        mediaAsset.getIsMain(),
                        mediaAsset.getSortOrder(),
                        mediaAsset.getAltText(),
                        mediaAsset.getContext()
                ))
                .toList();
    }
}
