package codin.msbackendcore.payments.application.internal.outboundservices;

import codin.msbackendcore.core.interfaces.acl.CoreContextFacade;
import codin.msbackendcore.payments.application.internal.dto.PlanDto;
import codin.msbackendcore.search.application.internal.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("ExternalCoreServiceForPayments")
public class ExternalCoreService {
    private final CoreContextFacade coreContextFacade;

    public ExternalCoreService(CoreContextFacade coreContextFacade) {
        this.coreContextFacade = coreContextFacade;
    }

    public boolean existTenantById(UUID tenantId) {
        return coreContextFacade.getTenantById(tenantId) != null;
    }

    public PlanDto getPlanByTenantId(UUID tenantId) {

        var planResponse = coreContextFacade.getPlanByTenantId(tenantId);

        return new PlanDto(
                planResponse.id(),
                planResponse.name(),
                planResponse.description(),
                planResponse.commissionRate(),
                planResponse.monthlyFee(),
                planResponse.onboardingFee(),
                planResponse.gmvMin(),
                planResponse.gmvMax(),
                planResponse.status(),
                planResponse.createdAt()
        );
    }
}
