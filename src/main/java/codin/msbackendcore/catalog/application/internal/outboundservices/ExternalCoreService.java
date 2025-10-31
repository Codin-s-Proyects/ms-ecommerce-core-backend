package codin.msbackendcore.catalog.application.internal.outboundservices;

import codin.msbackendcore.core.interfaces.acl.CoreContextFacade;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("ExternalCoreServiceForCatalog")
public class ExternalCoreService {
    private final CoreContextFacade coreContextFacade;

    public ExternalCoreService(CoreContextFacade coreContextFacade) {
        this.coreContextFacade = coreContextFacade;
    }

    public boolean existTenantById(UUID tenantId) {
        return coreContextFacade.getTenantById(tenantId) != null;
    }
}
