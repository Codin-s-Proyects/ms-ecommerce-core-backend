package codin.msbackendcore.pricing.application.internal.commandservice;

import codin.msbackendcore.pricing.domain.model.commands.CreateProductDiscountCommand;
import codin.msbackendcore.pricing.domain.model.entities.ProductDiscount;
import codin.msbackendcore.pricing.domain.services.discount.DiscountDomainService;
import codin.msbackendcore.pricing.domain.services.productdiscount.ProductDiscountCommandService;
import codin.msbackendcore.pricing.domain.services.productdiscount.ProductDiscountDomainService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProductDiscountCommandServiceImpl implements ProductDiscountCommandService {

    private final ProductDiscountDomainService productDiscountDomainService;
    private final DiscountDomainService discountDomainService;

    public ProductDiscountCommandServiceImpl(ProductDiscountDomainService productDiscountDomainService, DiscountDomainService discountDomainService) {
        this.productDiscountDomainService = productDiscountDomainService;
        this.discountDomainService = discountDomainService;
    }

    @Transactional
    @Override
    public ProductDiscount handle(CreateProductDiscountCommand command) {

        var discount = discountDomainService.getDiscountByTenantAndId(command.tenantId(), command.discountId());

        return productDiscountDomainService.createProductDiscount(
                command.tenantId(),
                command.productVariantId(),
                discount
        );

    }
}
