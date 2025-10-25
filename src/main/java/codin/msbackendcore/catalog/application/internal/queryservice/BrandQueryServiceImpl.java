package codin.msbackendcore.catalog.application.internal.queryservice;

import codin.msbackendcore.catalog.domain.model.entities.Brand;
import codin.msbackendcore.catalog.domain.model.queries.brand.GetAllBrandByTenantIdQuery;
import codin.msbackendcore.catalog.domain.services.brand.BrandDomainService;
import codin.msbackendcore.catalog.domain.services.brand.BrandQueryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandQueryServiceImpl implements BrandQueryService {

    private final BrandDomainService brandDomainService;

    public BrandQueryServiceImpl(BrandDomainService brandDomainService) {
        this.brandDomainService = brandDomainService;
    }

    @Override
    public List<Brand> handle(GetAllBrandByTenantIdQuery query) {
        return brandDomainService.getAllBrandsByTenantId(query.tenantId());
    }
}
