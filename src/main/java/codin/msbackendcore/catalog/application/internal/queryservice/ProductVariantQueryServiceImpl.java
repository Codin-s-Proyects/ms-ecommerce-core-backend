package codin.msbackendcore.catalog.application.internal.queryservice;

import codin.msbackendcore.catalog.application.internal.outboundservices.ExternalCoreService;
import codin.msbackendcore.catalog.application.internal.outboundservices.ExternalPricingService;
import codin.msbackendcore.catalog.domain.model.queries.productvariant.GetProductVariantByProductAndTenantIdQuery;
import codin.msbackendcore.catalog.domain.services.product.ProductDomainService;
import codin.msbackendcore.catalog.domain.services.productvariant.ProductVariantDomainService;
import codin.msbackendcore.catalog.domain.services.productvariant.ProductVariantQueryService;
import codin.msbackendcore.catalog.interfaces.dto.product.ProductWithAssetsResponse;
import codin.msbackendcore.catalog.interfaces.dto.productvariant.ProductVariantDetailResponse;
import codin.msbackendcore.catalog.interfaces.dto.productvariant.ProductVariantResponse;
import codin.msbackendcore.core.domain.model.valueobjects.EntityType;
import org.springframework.stereotype.Service;

@Service
public class ProductVariantQueryServiceImpl implements ProductVariantQueryService {

    private final ProductVariantDomainService productVariantDomainService;
    private final ProductDomainService productDomainService;
    private final ExternalPricingService externalPricingService;
    private final ExternalCoreService externalCoreService;

    public ProductVariantQueryServiceImpl(ProductVariantDomainService productVariantDomainService, ProductDomainService productDomainService, ExternalPricingService externalPricingService, ExternalCoreService externalCoreService) {
        this.productVariantDomainService = productVariantDomainService;
        this.productDomainService = productDomainService;
        this.externalPricingService = externalPricingService;
        this.externalCoreService = externalCoreService;
    }

    @Override
    public ProductVariantDetailResponse handle(GetProductVariantByProductAndTenantIdQuery query) {
        var product = productDomainService.getProductById(query.productId(), query.tenantId());
        var productMediaAssets = externalCoreService.getMediaAssetByEntityIdAndEntityType(product.getTenantId(), product.getId(), EntityType.PRODUCT);
        var productVariants = productVariantDomainService.getVariantsByProductId(product, query.tenantId());

        var variants = productVariants.stream().map(variant -> {
            var mediaAssets = externalCoreService.getMediaAssetByEntityIdAndEntityType(variant.getTenantId(), variant.getId(), EntityType.PRODUCT_VARIANT);
            var productPrices = externalPricingService.getProductPriceByVariantId(variant.getTenantId(), variant.getId());

            var productVariantResponse = new ProductVariantResponse(
                    variant.getId(),
                    variant.getProduct().getId(),
                    variant.getTenantId(),
                    variant.getSku(),
                    variant.getName(),
                    variant.getAttributes(),
                    variant.getProductQuantity(),
                    variant.getStatus().name()
            );

            return new ProductVariantDetailResponse.VariantDetailResponse(
                    productVariantResponse,
                    mediaAssets,
                    productPrices
            );
        }).toList();

        var productResponse = new ProductWithAssetsResponse(
                product.getId(),
                product.getTenantId(),
                product.getName(),
                product.getSlug(),
                product.getDescription(),
                product.isHasVariants(),
                product.getStatus().name(),
                productMediaAssets
        );

        return new ProductVariantDetailResponse(
                productResponse,
                variants
        );

    }
}
