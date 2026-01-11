package codin.msbackendcore.catalog.application.internal.queryservice;

import codin.msbackendcore.catalog.application.internal.dto.ProductDetailDto;
import codin.msbackendcore.catalog.application.internal.outboundservices.ExternalCoreService;
import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.model.queries.product.*;
import codin.msbackendcore.catalog.domain.services.brand.BrandDomainService;
import codin.msbackendcore.catalog.domain.services.category.CategoryDomainService;
import codin.msbackendcore.catalog.domain.services.product.ProductDomainService;
import codin.msbackendcore.catalog.domain.services.product.ProductQueryService;
import codin.msbackendcore.catalog.domain.services.productvariant.ProductVariantDomainService;
import codin.msbackendcore.catalog.interfaces.dto.product.ProductResponse;
import codin.msbackendcore.catalog.interfaces.dto.product.ProductWithStockResponse;
import codin.msbackendcore.core.domain.model.valueobjects.EntityType;
import codin.msbackendcore.shared.infrastructure.pagination.model.CursorPage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProductQueryServiceImpl implements ProductQueryService {
    private final ProductDomainService productDomainService;
    private final ProductVariantDomainService productVariantDomainService;
    private final CategoryDomainService categoryDomainService;
    private final BrandDomainService brandDomainService;
    private final ExternalCoreService externalCoreService;

    public ProductQueryServiceImpl(ProductDomainService productDomainService, ProductVariantDomainService productVariantDomainService, CategoryDomainService categoryDomainService, BrandDomainService brandDomainService, ExternalCoreService externalCoreService) {
        this.productDomainService = productDomainService;
        this.productVariantDomainService = productVariantDomainService;
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
    public List<ProductWithStockResponse> handle(GetAllProductByCategoryAndTenantIdQuery query) {
        var category = categoryDomainService.getCategoryById(query.categoryId());


        return productDomainService.getProductsByCategory(query.tenantId(), category)
                .stream()
                .map(product -> {

                    int totalStock = productVariantDomainService
                            .getVariantsByProductId(product, product.getTenantId())
                            .stream()
                            .mapToInt(variant ->
                                    variant.getProductQuantity() - variant.getReservedQuantity()
                            )
                            .sum();

                    return new ProductWithStockResponse(
                            product.getId(),
                            product.getTenantId(),
                            product.getName(),
                            product.getSlug(),
                            product.getDescription(),
                            product.isHasVariants(),
                            product.getStatus().name(),
                            totalStock
                    );
                })
                .toList();
    }

    @Override
    public CursorPage<ProductWithStockResponse> handle(GetAllProductPaginatedByCategoryAndTenantIdQuery query) {
        var category = categoryDomainService.getCategoryById(query.categoryId());

        var cursorProduct = productDomainService.getProductsByCategory(query.tenantId(), category.getId(), query.paginationQuery());
        var productWithStockResponse = cursorProduct.data()
                .stream()
                .map(product -> {

                    int totalStock = productVariantDomainService
                            .getVariantsByProductId(product, product.getTenantId())
                            .stream()
                            .mapToInt(variant ->
                                    variant.getProductQuantity() - variant.getReservedQuantity()
                            )
                            .sum();

                    return new ProductWithStockResponse(
                            product.getId(),
                            product.getTenantId(),
                            product.getName(),
                            product.getSlug(),
                            product.getDescription(),
                            product.isHasVariants(),
                            product.getStatus().name(),
                            totalStock
                    );
                })
                .toList();

        return new CursorPage<>(productWithStockResponse, cursorProduct.nextCursor(), cursorProduct.hasMore(), cursorProduct.totalApprox());

    }

    @Override
    public List<Product> handle(GetAllProductByBrandAndTenantIdQuery query) {
        var brand = brandDomainService.getBrandById(query.brandId());

        return productDomainService.getProductsByBrand(query.tenantId(), brand);
    }
}
