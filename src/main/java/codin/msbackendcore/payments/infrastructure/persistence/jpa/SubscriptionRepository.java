package codin.msbackendcore.payments.infrastructure.persistence.jpa;

import codin.msbackendcore.payments.domain.model.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    Optional<Subscription> findByTenantId(UUID tenantId);
}
