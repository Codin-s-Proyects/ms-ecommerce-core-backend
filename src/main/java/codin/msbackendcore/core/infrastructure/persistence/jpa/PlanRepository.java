package codin.msbackendcore.core.infrastructure.persistence.jpa;

import codin.msbackendcore.core.domain.model.entities.Plan;
import codin.msbackendcore.core.domain.model.entities.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PlanRepository extends JpaRepository<Plan, UUID> {
}
