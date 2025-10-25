package codin.msbackendcore.pricing.infrastructure.jpa;

import codin.msbackendcore.pricing.domain.model.entities.Discount;
import codin.msbackendcore.pricing.domain.model.entities.PriceList;
import codin.msbackendcore.pricing.domain.model.entities.ProductDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductDiscountRepository extends JpaRepository<ProductDiscount, UUID> {
    boolean existsByTenantIdAndProductVariantIdAndDiscount(UUID tenantId, UUID productVariantId, Discount discount);
}
