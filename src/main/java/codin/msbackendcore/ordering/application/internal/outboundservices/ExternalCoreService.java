package codin.msbackendcore.ordering.application.internal.outboundservices;

import codin.msbackendcore.core.domain.model.valueobjects.EntityType;
import codin.msbackendcore.core.interfaces.acl.CoreContextFacade;
import codin.msbackendcore.core.interfaces.dto.mediaasset.MediaAssetResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("ExternalCoreServiceForOrdering")
public class ExternalCoreService {
    private final CoreContextFacade coreContextFacade;

    public ExternalCoreService(CoreContextFacade coreContextFacade) {
        this.coreContextFacade = coreContextFacade;
    }

    public boolean existTenantById(UUID tenantId) {
        return coreContextFacade.getTenantById(tenantId) != null;
    }

    public List<MediaAssetResponse> getMediaAssetsByProductVariant(UUID tenantId, UUID productVariantId) {
        return coreContextFacade.getMediaAssetByEntityIdAndEntityType(tenantId, EntityType.PRODUCT_VARIANT , productVariantId);
    }
}
