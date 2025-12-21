package codin.msbackendcore.core.application.internal.domainservice;

import codin.msbackendcore.core.domain.model.entities.Plan;
import codin.msbackendcore.core.domain.services.tenant.PlanDomainService;
import codin.msbackendcore.core.infrastructure.persistence.jpa.PlanRepository;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PlanDomainServiceImpl implements PlanDomainService {

    private final PlanRepository planRepository;

    public PlanDomainServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public Plan getPlanById(UUID planId) {
        return planRepository.findById(planId)
                .orElseThrow(() -> new NotFoundException("error.not_found", new String[]{planId.toString()}, "planId"));
    }
}
