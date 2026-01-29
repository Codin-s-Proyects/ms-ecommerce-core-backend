package codin.msbackendcore.payments.infrastructure.persistence.jpa;

import codin.msbackendcore.payments.domain.model.entities.TenantPayoutItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TenantPayoutItemRepository extends JpaRepository<TenantPayoutItem, UUID> {
}
