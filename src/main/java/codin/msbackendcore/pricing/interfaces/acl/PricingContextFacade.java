package codin.msbackendcore.pricing.interfaces.acl;

import codin.msbackendcore.pricing.domain.services.productprice.ProductPriceDomainService;
import codin.msbackendcore.pricing.interfaces.dto.productprice.ProductPriceResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PricingContextFacade {
    private final ProductPriceDomainService productPriceDomainService;

    public PricingContextFacade(ProductPriceDomainService productPriceDomainService) {
        this.productPriceDomainService = productPriceDomainService;
    }

    public List<ProductPriceResponse> getProductVariantById(UUID tenantId, UUID productVariantId) {
        return productPriceDomainService.getProductPricesByProductVariantId(tenantId, productVariantId)
                .stream()
                .map(pp -> new ProductPriceResponse(
                        pp.getId(),
                        pp.getTenantId(),
                        pp.getProductVariantId(),
                        pp.getPriceList().getId(),
                        pp.getDiscountPercent(),
                        pp.getFinalPrice(),
                        pp.getBasePrice(),
                        pp.getMinQuantity(),
                        pp.getValidFrom(),
                        pp.getValidTo()
                ))
                .toList();
    }
}
