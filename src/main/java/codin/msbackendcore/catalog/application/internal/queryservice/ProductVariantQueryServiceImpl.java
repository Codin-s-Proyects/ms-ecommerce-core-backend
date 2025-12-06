package codin.msbackendcore.catalog.application.internal.queryservice;

import codin.msbackendcore.catalog.application.internal.dto.ProductVariantDetailDto;
import codin.msbackendcore.catalog.application.internal.outboundservices.ExternalCoreService;
import codin.msbackendcore.catalog.application.internal.outboundservices.ExternalPricingService;
import codin.msbackendcore.catalog.domain.model.queries.productvariant.GetProductVariantByProductAndTenantIdQuery;
import codin.msbackendcore.catalog.domain.services.product.ProductDomainService;
import codin.msbackendcore.catalog.domain.services.productvariant.ProductVariantDomainService;
import codin.msbackendcore.catalog.domain.services.productvariant.ProductVariantQueryService;
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
    public ProductVariantDetailDto handle(GetProductVariantByProductAndTenantIdQuery query) {
        var product = productDomainService.getProductById(query.productId());
        var productVariants = productVariantDomainService.getVariantsByProductId(product, query.tenantId());

        var variants = productVariants.stream().map(variant -> {
            var mediaAssets = externalCoreService.getMediaAssetsByVariantId(variant.getTenantId(), variant.getId());
            var productPrices = externalPricingService.getProductPriceByVariantId(variant.getTenantId(), variant.getId());

            return new ProductVariantDetailDto.VariantDetailDto(
                    variant,
                    mediaAssets,
                    productPrices
            );
        }).toList();

        return new ProductVariantDetailDto(
                product,
                variants
        );

    }
}
