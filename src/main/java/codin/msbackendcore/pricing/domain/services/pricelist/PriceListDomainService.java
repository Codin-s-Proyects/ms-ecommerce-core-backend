package codin.msbackendcore.pricing.domain.services.pricelist;


import codin.msbackendcore.pricing.domain.model.entities.PriceList;

import java.time.Instant;
import java.util.UUID;

public interface PriceListDomainService {
    PriceList createPriceList(UUID tenantId, String name, String description, String currencyCode, Instant validFrom, Instant validTo);
    PriceList getPriceListByTenantAndId(UUID tenantId, UUID priceListId);
    void deletePriceList(UUID tenantId, UUID priceListId);
}