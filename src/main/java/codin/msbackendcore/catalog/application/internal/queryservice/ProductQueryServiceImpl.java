package codin.msbackendcore.catalog.application.internal.queryservice;

import codin.msbackendcore.catalog.application.internal.dto.ProductDetailDto;
import codin.msbackendcore.catalog.application.internal.outboundservices.ExternalCoreService;
import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.model.queries.product.*;
import codin.msbackendcore.catalog.domain.services.brand.BrandDomainService;
import codin.msbackendcore.catalog.domain.services.category.CategoryDomainService;
import codin.msbackendcore.catalog.domain.services.product.ProductDomainService;
import codin.msbackendcore.catalog.domain.services.product.ProductQueryService;
import codin.msbackendcore.core.domain.model.valueobjects.EntityType;
import codin.msbackendcore.shared.infrastructure.pagination.model.CursorPage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductQueryServiceImpl implements ProductQueryService {
    private final ProductDomainService productDomainService;
    private final CategoryDomainService categoryDomainService;
    private final BrandDomainService brandDomainService;
    private final ExternalCoreService externalCoreService;

    public ProductQueryServiceImpl(ProductDomainService productDomainService, CategoryDomainService categoryDomainService, BrandDomainService brandDomainService, ExternalCoreService externalCoreService) {
        this.productDomainService = productDomainService;
        this.categoryDomainService = categoryDomainService;
        this.brandDomainService = brandDomainService;
        this.externalCoreService = externalCoreService;
    }

    @Override
    public CursorPage<ProductDetailDto> handle(GetAllProductPaginatedByTenantIdQuery query) {
        var cursorProduct = productDomainService.getProductsByTenantId(query.tenantId(), query.paginationQuery());

        var listProductDetailDto = cursorProduct.data()
                .stream()
                .map(product -> {

                    var mediaAsset = externalCoreService.getMediaAssetByEntityIdAndEntityType(query.tenantId(), product.getId(), EntityType.PRODUCT);

                    return new ProductDetailDto(product, mediaAsset);
                })
                .toList();

        return new CursorPage<>(listProductDetailDto, cursorProduct.nextCursor(), cursorProduct.hasMore(), cursorProduct.totalApprox());
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
