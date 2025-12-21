package codin.msbackendcore.pricing.application.internal.domainservice;

import codin.msbackendcore.pricing.domain.model.entities.PriceList;
import codin.msbackendcore.pricing.domain.model.entities.ProductPrice;
import codin.msbackendcore.pricing.domain.services.productprice.ProductPriceDomainService;
import codin.msbackendcore.pricing.infrastructure.jpa.ProductPriceRepository;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
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

        if (productPriceRepository.existsByTenantIdAndProductVariantIdAndPriceList(tenantId, productVariantId, priceList)) {
            throw new BadRequestException("error.bad_request", new String[]{priceList.getId().toString()}, "priceList");
        }

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

    @Override
    public ProductPrice updateProductPrice(UUID productPriceId, UUID tenantId, PriceList priceList, BigDecimal basePrice, Integer minQuantity, Instant validTo) {
        var productPrice = productPriceRepository.findByIdAndTenantId(productPriceId, tenantId)
                .orElseThrow(() ->
                        new NotFoundException("error.not_found", new String[]{productPriceId.toString()}, "productPriceId")
                );

        productPrice.setPriceList(priceList);
        productPrice.setBasePrice(basePrice);
        productPrice.setMinQuantity(minQuantity);
        productPrice.setValidTo(validTo);

        return productPriceRepository.save(productPrice);
    }

    @Override
    public List<ProductPrice> getProductPricesByProductVariantId(UUID tenantId, UUID productVariantId) {
        return productPriceRepository.findAllByTenantIdAndProductVariantId(tenantId, productVariantId);
    }
}
