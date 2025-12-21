package codin.msbackendcore.pricing.domain.services.pricelist;

import codin.msbackendcore.pricing.domain.model.entities.PriceList;
import codin.msbackendcore.pricing.domain.model.queries.GetAllPriceListByTenantIdQuery;

import java.util.List;

public interface PriceListQueryService {
    List<PriceList> handle(GetAllPriceListByTenantIdQuery query);
}
