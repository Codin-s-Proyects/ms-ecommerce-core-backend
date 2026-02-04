package codin.msbackendcore.core.domain.services.plan;

import codin.msbackendcore.core.domain.model.entities.Plan;

import java.util.UUID;

public interface PlanDomainService {
    Plan getPlanById(UUID planId);
}
