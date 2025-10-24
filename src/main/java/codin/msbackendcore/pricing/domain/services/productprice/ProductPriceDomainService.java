package codin.msbackendcore.pricing.domain.services.productprice;


import codin.msbackendcore.pricing.domain.model.entities.PriceList;
import codin.msbackendcore.pricing.domain.model.entities.ProductPrice;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public interface ProductPriceDomainService {
    ProductPrice createProductPrice(UUID tenantId, UUID productVariantId, PriceList priceList, BigDecimal basePrice, Integer minQuantity, Instant validTo);
}