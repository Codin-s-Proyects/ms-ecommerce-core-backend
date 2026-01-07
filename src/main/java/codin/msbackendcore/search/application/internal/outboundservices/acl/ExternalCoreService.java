package codin.msbackendcore.search.application.internal.outboundservices.acl;

import codin.msbackendcore.core.domain.model.valueobjects.EntityType;
import codin.msbackendcore.core.interfaces.acl.CoreContextFacade;
import codin.msbackendcore.search.application.internal.dto.MediaAssetDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("ExternalCoreServiceForSearch")
public class ExternalCoreService {
    private final CoreContextFacade coreContextFacade;

    public ExternalCoreService(CoreContextFacade coreContextFacade) {
        this.coreContextFacade = coreContextFacade;
    }

    public List<MediaAssetDto> getMediaAssetsByVariantId(UUID tenantId, UUID variantId) {
        var mediaAssetResponseList = coreContextFacade.getMediaAssetByEntityIdAndEntityType(tenantId, EntityType.PRODUCT_VARIANT, variantId);

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
