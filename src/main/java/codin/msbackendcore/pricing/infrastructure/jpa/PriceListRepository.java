package codin.msbackendcore.pricing.infrastructure.jpa;

import codin.msbackendcore.pricing.domain.model.entities.PriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, UUID> {
    Optional<PriceList> findPriceListByTenantIdAndId(UUID tenantId, UUID priceListId);
}
