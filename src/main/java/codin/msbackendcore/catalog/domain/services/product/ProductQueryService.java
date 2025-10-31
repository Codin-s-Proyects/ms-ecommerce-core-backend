package codin.msbackendcore.catalog.domain.services.product;

import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.model.queries.product.GetAllProductByBrandAndTenantIdQuery;
import codin.msbackendcore.catalog.domain.model.queries.product.GetAllProductByCategoryAndTenantIdQuery;

import java.util.List;

public interface ProductQueryService {
    List<Product> handle(GetAllProductByCategoryAndTenantIdQuery query);
    List<Product> handle(GetAllProductByBrandAndTenantIdQuery query);
}
