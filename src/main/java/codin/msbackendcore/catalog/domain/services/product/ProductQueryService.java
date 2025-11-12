package codin.msbackendcore.catalog.domain.services.product;

import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.model.queries.product.GetAllProductByBrandAndTenantIdQuery;
import codin.msbackendcore.catalog.domain.model.queries.product.GetAllProductByCategoryAndTenantIdQuery;
import codin.msbackendcore.catalog.domain.model.queries.product.GetAllProductPaginatedByCategoryAndTenantIdQuery;
import codin.msbackendcore.shared.infrastructure.pagination.model.CursorPage;

import java.util.List;

public interface ProductQueryService {
    List<Product> handle(GetAllProductByCategoryAndTenantIdQuery query);
    CursorPage<Product> handle(GetAllProductPaginatedByCategoryAndTenantIdQuery query);
    List<Product> handle(GetAllProductByBrandAndTenantIdQuery query);
}
