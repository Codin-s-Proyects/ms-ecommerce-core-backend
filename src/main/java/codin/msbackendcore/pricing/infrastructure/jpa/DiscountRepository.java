package codin.msbackendcore.pricing.infrastructure.jpa;

import codin.msbackendcore.pricing.domain.model.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, UUID> {
    Optional<Discount> findByTenantIdAndId(UUID tenantId, UUID discountId);
}
