package codin.msbackendcore.pricing.domain.services.pricinglist;


import codin.msbackendcore.pricing.domain.model.entities.PriceList;

import java.time.Instant;
import java.util.UUID;

public interface PriceListDomainService {
    PriceList createPriceList(UUID tenantId, String code, String name, String description, String currencyCode, Instant validFrom, Instant validTo);
    PriceList getPriceListByTenantAndId(UUID tenantId, UUID priceListId);
}