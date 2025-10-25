package codin.msbackendcore.pricing.domain.services.productdiscount;


import codin.msbackendcore.pricing.domain.model.entities.Discount;
import codin.msbackendcore.pricing.domain.model.entities.ProductDiscount;

import java.util.UUID;

public interface ProductDiscountDomainService {
    ProductDiscount createProductDiscount(UUID tenantId, UUID productVariantId, Discount discount);
}