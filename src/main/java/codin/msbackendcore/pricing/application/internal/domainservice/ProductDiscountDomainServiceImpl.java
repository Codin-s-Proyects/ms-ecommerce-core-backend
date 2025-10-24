package codin.msbackendcore.pricing.application.internal.domainservice;

import codin.msbackendcore.pricing.domain.model.entities.Discount;
import codin.msbackendcore.pricing.domain.model.entities.ProductDiscount;
import codin.msbackendcore.pricing.domain.services.productdiscount.ProductDiscountDomainService;
import codin.msbackendcore.pricing.infrastructure.jpa.ProductDiscountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductDiscountDomainServiceImpl implements ProductDiscountDomainService {

    private final ProductDiscountRepository productDiscountRepository;

    public ProductDiscountDomainServiceImpl(ProductDiscountRepository productDiscountRepository) {
        this.productDiscountRepository = productDiscountRepository;
    }

    @Transactional
    @Override
    public ProductDiscount createProductDiscount(UUID tenantId, UUID productVariantId, Discount discount) {

        var productDiscount = ProductDiscount.builder()
                .tenantId(tenantId)
                .productVariantId(productVariantId)
                .discount(discount)
                .build();

        return productDiscountRepository.save(productDiscount);
    }
}
