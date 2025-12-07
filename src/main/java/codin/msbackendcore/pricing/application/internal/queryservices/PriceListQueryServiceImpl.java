package codin.msbackendcore.pricing.application.internal.queryservices;

import codin.msbackendcore.pricing.domain.model.entities.PriceList;
import codin.msbackendcore.pricing.domain.model.queries.GetAllPriceListByTenantIdQuery;
import codin.msbackendcore.pricing.domain.services.pricelist.PriceListDomainService;
import codin.msbackendcore.pricing.domain.services.pricelist.PriceListQueryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceListQueryServiceImpl implements PriceListQueryService {

    private final PriceListDomainService priceListDomainService;

    public PriceListQueryServiceImpl(PriceListDomainService priceListDomainService) {
        this.priceListDomainService = priceListDomainService;
    }

    @Override
    public List<PriceList> handle(GetAllPriceListByTenantIdQuery query) {
        return priceListDomainService.getPriceListsByTenantId(query.tenantId());
    }
}
