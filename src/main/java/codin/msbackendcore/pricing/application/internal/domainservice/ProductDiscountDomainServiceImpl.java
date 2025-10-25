package codin.msbackendcore.pricing.application.internal.domainservice;

import codin.msbackendcore.pricing.domain.model.entities.Discount;
import codin.msbackendcore.pricing.domain.model.entities.ProductDiscount;
import codin.msbackendcore.pricing.domain.services.productdiscount.ProductDiscountDomainService;
import codin.msbackendcore.pricing.infrastructure.jpa.ProductDiscountRepository;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
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
        if (productDiscountRepository.existsByTenantIdAndProductVariantIdAndDiscount(tenantId, productVariantId, discount)) {
            throw new BadRequestException("error.bad_request", new String[]{discount.getId().toString()}, "discount");
        }

        var productDiscount = ProductDiscount.builder()
                .tenantId(tenantId)
                .productVariantId(productVariantId)
                .discount(discount)
                .build();

        return productDiscountRepository.save(productDiscount);
    }
}
