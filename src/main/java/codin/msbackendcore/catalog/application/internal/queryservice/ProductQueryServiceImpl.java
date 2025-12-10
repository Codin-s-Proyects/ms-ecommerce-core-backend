package codin.msbackendcore.catalog.application.internal.queryservice;

import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.model.queries.product.*;
import codin.msbackendcore.catalog.domain.services.brand.BrandDomainService;
import codin.msbackendcore.catalog.domain.services.category.CategoryDomainService;
import codin.msbackendcore.catalog.domain.services.product.ProductDomainService;
import codin.msbackendcore.catalog.domain.services.product.ProductQueryService;
import codin.msbackendcore.shared.infrastructure.pagination.model.CursorPage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductQueryServiceImpl implements ProductQueryService {
    private final ProductDomainService productDomainService;
    private final CategoryDomainService categoryDomainService;
    private final BrandDomainService brandDomainService;

    public ProductQueryServiceImpl(ProductDomainService productDomainService, CategoryDomainService categoryDomainService, BrandDomainService brandDomainService) {
        this.productDomainService = productDomainService;
        this.categoryDomainService = categoryDomainService;
        this.brandDomainService = brandDomainService;
    }

    @Override
    public CursorPage<Product> handle(GetAllProductPaginatedByTenantIdQuery query) {
        return productDomainService.getProductsByTenantId(query.tenantId(), query.paginationQuery());
    }

    @Override
    public Product handle(GetProductByIdQuery query) {
        return productDomainService.getProductById(query.productId());
    }

    @Override
    public List<Product> handle(GetAllProductByCategoryAndTenantIdQuery query) {
        var category = categoryDomainService.getCategoryById(query.categoryId());

        return productDomainService.getProductsByCategory(query.tenantId(), category);
    }

    @Override
    public CursorPage<Product> handle(GetAllProductPaginatedByCategoryAndTenantIdQuery query) {
        var category = categoryDomainService.getCategoryById(query.categoryId());

        return productDomainService.getProductsByCategory(query.tenantId(), category.getId(), query.paginationQuery());
    }

    @Override
    public List<Product> handle(GetAllProductByBrandAndTenantIdQuery query) {
        var brand = brandDomainService.getBrandById(query.brandId());

        return productDomainService.getProductsByBrand(query.tenantId(), brand);
    }
}
