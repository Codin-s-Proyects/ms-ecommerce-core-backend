package codin.msbackendcore.pricing.infrastructure.jpa;

import codin.msbackendcore.pricing.domain.model.entities.PriceList;
import codin.msbackendcore.pricing.domain.model.entities.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPrice, UUID> {
    boolean existsByTenantIdAndProductVariantIdAndPriceList(UUID tenantId, UUID productVariantId, PriceList priceList);
}
