package codin.msbackendcore.pricing.application.internal.domainservice;

import codin.msbackendcore.pricing.domain.model.entities.PriceList;
import codin.msbackendcore.pricing.domain.model.entities.ProductPrice;
import codin.msbackendcore.pricing.domain.services.productprice.ProductPriceDomainService;
import codin.msbackendcore.pricing.infrastructure.jpa.ProductPriceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
public class ProductPriceDomainServiceImpl implements ProductPriceDomainService {

    private final ProductPriceRepository productPriceRepository;

    public ProductPriceDomainServiceImpl(ProductPriceRepository productPriceRepository) {
        this.productPriceRepository = productPriceRepository;
    }

    @Transactional
    @Override
    public ProductPrice createProductPrice(UUID tenantId, UUID productVariantId, PriceList priceList, BigDecimal basePrice, Integer minQuantity, Instant validTo) {

        var productPrice = ProductPrice.builder()
                .tenantId(tenantId)
                .productVariantId(productVariantId)
                .priceList(priceList)
                .basePrice(basePrice)
                .minQuantity(minQuantity)
                .validTo(validTo)
                .build();

        return productPriceRepository.save(productPrice);
    }
}
