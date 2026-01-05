package codin.msbackendcore.pricing.interfaces.acl;

import codin.msbackendcore.pricing.domain.services.pricelist.PriceListDomainService;
import codin.msbackendcore.pricing.domain.services.productprice.ProductPriceDomainService;
import codin.msbackendcore.pricing.interfaces.dto.pricelist.PriceListResponse;
import codin.msbackendcore.pricing.interfaces.dto.productprice.ProductPriceResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PricingContextFacade {
    private final ProductPriceDomainService productPriceDomainService;
    private final PriceListDomainService priceListDomainService;

    public PricingContextFacade(ProductPriceDomainService productPriceDomainService, PriceListDomainService priceListDomainService) {
        this.productPriceDomainService = productPriceDomainService;
        this.priceListDomainService = priceListDomainService;
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

    public PriceListResponse getPriceList(UUID tenantId, UUID productVariantId) {
        var priceList = priceListDomainService.getPriceListByTenantAndId(tenantId, productVariantId);
        return new PriceListResponse(
                priceList.getId(),
                priceList.getTenantId(),
                priceList.getCode(),
                priceList.getName(),
                priceList.getDescription(),
                priceList.getCurrencyCode(),
                priceList.getValidFrom(),
                priceList.getValidTo(),
                priceList.getStatus().name()
        );
    }
}
