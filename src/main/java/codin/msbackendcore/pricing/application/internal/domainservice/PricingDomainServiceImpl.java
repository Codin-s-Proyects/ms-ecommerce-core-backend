package codin.msbackendcore.pricing.application.internal.domainservice;

import codin.msbackendcore.pricing.domain.model.entities.PriceList;
import codin.msbackendcore.pricing.domain.model.entities.ProductPrice;
import codin.msbackendcore.pricing.domain.services.PricingDomainService;
import codin.msbackendcore.pricing.infrastructure.jpa.PriceListRepository;
import codin.msbackendcore.pricing.infrastructure.jpa.ProductPriceRepository;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

import static codin.msbackendcore.shared.infrastructure.utils.Constants.RETAIL_PRICE_LIST_CODE;
import static codin.msbackendcore.shared.infrastructure.utils.Constants.WHOLESALE_PRICE_LIST_CODE;

@Service
@Transactional
public class PricingDomainServiceImpl implements PricingDomainService {

    private final PriceListRepository priceListRepository;
    private final ProductPriceRepository productPriceRepository;

    public PricingDomainServiceImpl(PriceListRepository priceListRepository, ProductPriceRepository productPriceRepository) {
        this.priceListRepository = priceListRepository;
        this.productPriceRepository = productPriceRepository;
    }

    @Override
    public void createDefaultPrices(UUID tenantId, UUID variantId, BigDecimal retailPrice, BigDecimal wholesalePrice) {
        PriceList retailList = priceListRepository.findByTenantIdAndCode(tenantId, RETAIL_PRICE_LIST_CODE)
                .orElseThrow(() -> new NotFoundException("error.not_found", new String[]{RETAIL_PRICE_LIST_CODE}, "code"));
        PriceList wholesaleList = priceListRepository.findByTenantIdAndCode(tenantId, WHOLESALE_PRICE_LIST_CODE)
                .orElseThrow(() -> new NotFoundException("error.not_found", new String[]{WHOLESALE_PRICE_LIST_CODE}, "code"));

        ProductPrice retailPriceEntry = ProductPrice.builder()
                .tenantId(tenantId)
                .productVariantId(variantId)
                .priceList(retailList)
                .basePrice(retailPrice)
                .finalPrice(retailPrice)
                .build();

        ProductPrice wholesalePriceEntry = ProductPrice.builder()
                .tenantId(tenantId)
                .productVariantId(variantId)
                .priceList(wholesaleList)
                .basePrice(wholesalePrice)
                .finalPrice(retailPrice)
                .build();


        productPriceRepository.save(retailPriceEntry);
        productPriceRepository.save(wholesalePriceEntry);
    }
}
