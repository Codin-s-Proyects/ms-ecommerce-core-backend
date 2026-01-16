package codin.msbackendcore.catalog.domain.services.product;

import codin.msbackendcore.catalog.application.internal.dto.ProductDetailDto;
import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.model.queries.product.*;
import codin.msbackendcore.catalog.interfaces.dto.product.ProductDetailWithStockResponse;
import codin.msbackendcore.shared.infrastructure.pagination.model.CursorPage;

import java.util.List;

public interface ProductQueryService {
    CursorPage<ProductDetailDto> handle(GetAllProductPaginatedByTenantIdQuery query);
    Product handle(GetProductByIdQuery query);
    List<ProductDetailWithStockResponse> handle(GetAllProductByCategoryAndTenantIdQuery query);
    CursorPage<ProductDetailWithStockResponse> handle(GetAllProductPaginatedByCategoryAndTenantIdQuery query);
    List<Product> handle(GetAllProductByBrandAndTenantIdQuery query);
}
