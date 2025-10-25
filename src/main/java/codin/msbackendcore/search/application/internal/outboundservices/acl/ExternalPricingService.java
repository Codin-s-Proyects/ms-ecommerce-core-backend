package codin.msbackendcore.search.application.internal.outboundservices.acl;

import codin.msbackendcore.pricing.interfaces.acl.PricingContextFacade;
import codin.msbackendcore.search.application.internal.dto.ProductPriceDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service()
public class ExternalPricingService {
    private final PricingContextFacade pricingContextFacade;

    public ExternalPricingService(PricingContextFacade pricingContextFacade) {
        this.pricingContextFacade = pricingContextFacade;
    }

    public List<ProductPriceDto> getProductPriceByVariantId(UUID tenantId, UUID variantId) {
        var productPriceResponseList = pricingContextFacade.getProductVariantById(tenantId, variantId);

        return productPriceResponseList.stream()
                .map(productPriceResponse -> new ProductPriceDto(
                        productPriceResponse.id(),
                        productPriceResponse.tenantId(),
                        productPriceResponse.productVariantId(),
                        productPriceResponse.priceListId(),
                        productPriceResponse.discountPercent(),
                        productPriceResponse.finalPrice(),
                        productPriceResponse.basePrice(),
                        productPriceResponse.minQuantity(),
                        productPriceResponse.validFrom(),
                        productPriceResponse.validTo()
                )).toList();
    }
}
