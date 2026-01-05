package codin.msbackendcore.search.application.internal.outboundservices.acl;

import codin.msbackendcore.pricing.interfaces.acl.PricingContextFacade;
import codin.msbackendcore.search.application.internal.dto.PriceListDto;
import codin.msbackendcore.search.application.internal.dto.ProductPriceDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("ExternalPricingServiceForSearch")
public class ExternalPricingService {
    private final PricingContextFacade pricingContextFacade;

    public ExternalPricingService(PricingContextFacade pricingContextFacade) {
        this.pricingContextFacade = pricingContextFacade;
    }

    public List<ProductPriceDto> getProductPriceByVariantId(UUID tenantId, UUID variantId) {
        var productPriceResponseList = pricingContextFacade.getProductVariantById(tenantId, variantId);

        return productPriceResponseList.stream()
                .map(productPriceResponse -> {

                    var priceList = getProductPriceByProductId(tenantId, productPriceResponse.priceListId());

                    return new ProductPriceDto(
                            productPriceResponse.id(),
                            productPriceResponse.tenantId(),
                            productPriceResponse.productVariantId(),
                            priceList,
                            productPriceResponse.discountPercent(),
                            productPriceResponse.finalPrice(),
                            productPriceResponse.basePrice(),
                            productPriceResponse.minQuantity(),
                            productPriceResponse.validFrom(),
                            productPriceResponse.validTo()
                    );
                }).toList();
    }

    public PriceListDto getProductPriceByProductId(UUID tenantId, UUID productId) {
        var priceListResponse = pricingContextFacade.getPriceList(tenantId, productId);

        return new PriceListDto(
                priceListResponse.id(),
                priceListResponse.tenantId(),
                priceListResponse.code(),
                priceListResponse.name(),
                priceListResponse.description(),
                priceListResponse.currencyCode(),
                priceListResponse.validFrom(),
                priceListResponse.validTo(),
                priceListResponse.status()
        );
    }
}
