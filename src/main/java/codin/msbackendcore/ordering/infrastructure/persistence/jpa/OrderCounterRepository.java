package codin.msbackendcore.ordering.infrastructure.persistence.jpa;

import codin.msbackendcore.ordering.domain.model.entities.OrderCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderCounterRepository extends JpaRepository<OrderCounter, UUID> {
    Optional<OrderCounter> findByTenantId(UUID tenantId);
}
