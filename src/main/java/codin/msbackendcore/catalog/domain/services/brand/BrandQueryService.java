package codin.msbackendcore.catalog.domain.services.brand;

import codin.msbackendcore.catalog.domain.model.entities.Brand;
import codin.msbackendcore.catalog.domain.model.queries.brand.GetAllBrandByTenantIdQuery;

import java.util.List;

public interface BrandQueryService {
    List<Brand> handle(GetAllBrandByTenantIdQuery query);
}
