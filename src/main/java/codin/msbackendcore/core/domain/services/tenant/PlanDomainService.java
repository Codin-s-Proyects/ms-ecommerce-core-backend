package codin.msbackendcore.core.domain.services.tenant;

import codin.msbackendcore.core.domain.model.entities.Plan;
import codin.msbackendcore.core.domain.model.entities.Tenant;

import java.util.UUID;

public interface PlanDomainService {
    Plan getPlanById(UUID planId);
}
