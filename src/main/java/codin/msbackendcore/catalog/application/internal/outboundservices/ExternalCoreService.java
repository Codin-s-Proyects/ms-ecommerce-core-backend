package codin.msbackendcore.catalog.application.internal.outboundservices;

import codin.msbackendcore.catalog.application.internal.dto.MediaAssetDto;
import codin.msbackendcore.core.domain.model.valueobjects.EntityType;
import codin.msbackendcore.core.interfaces.acl.CoreContextFacade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("ExternalCoreServiceForCatalog")
public class ExternalCoreService {
    private final CoreContextFacade coreContextFacade;

    public ExternalCoreService(CoreContextFacade coreContextFacade) {
        this.coreContextFacade = coreContextFacade;
    }

    public List<MediaAssetDto> getMediaAssetByEntityIdAndEntityType(UUID tenantId, UUID entityId, EntityType entityType) {
        var mediaAssetResponseList = coreContextFacade.getMediaAssetByEntityIdAndEntityType(tenantId, entityType, entityId);

        return mediaAssetResponseList.stream()
                .map(mediaAssetResponse -> new MediaAssetDto(
                        mediaAssetResponse.id(),
                        mediaAssetResponse.entityType(),
                        mediaAssetResponse.entityId(),
                        mediaAssetResponse.url(),
                        mediaAssetResponse.publicId(),
                        mediaAssetResponse.isMain(),
                        mediaAssetResponse.sortOrder(),
                        mediaAssetResponse.assetMeta(),
                        mediaAssetResponse.context(),
                        mediaAssetResponse.usage(),
                        mediaAssetResponse.aiContext()
                )).toList();
    }

    public boolean existTenantById(UUID tenantId) {
        return coreContextFacade.getTenantById(tenantId) != null;
    }
}
