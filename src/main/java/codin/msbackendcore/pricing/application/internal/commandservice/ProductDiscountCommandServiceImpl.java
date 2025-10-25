package codin.msbackendcore.pricing.application.internal.commandservice;

import codin.msbackendcore.pricing.application.internal.outboundservices.ExternalCatalogService;
import codin.msbackendcore.pricing.domain.model.commands.CreateProductDiscountCommand;
import codin.msbackendcore.pricing.domain.model.entities.ProductDiscount;
import codin.msbackendcore.pricing.domain.services.discount.DiscountDomainService;
import codin.msbackendcore.pricing.domain.services.productdiscount.ProductDiscountCommandService;
import codin.msbackendcore.pricing.domain.services.productdiscount.ProductDiscountDomainService;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProductDiscountCommandServiceImpl implements ProductDiscountCommandService {

    private final ProductDiscountDomainService productDiscountDomainService;
    private final DiscountDomainService discountDomainService;
    private final ExternalCatalogService externalCatalogService;

    public ProductDiscountCommandServiceImpl(ProductDiscountDomainService productDiscountDomainService, DiscountDomainService discountDomainService, ExternalCatalogService externalCatalogService) {
        this.productDiscountDomainService = productDiscountDomainService;
        this.discountDomainService = discountDomainService;
        this.externalCatalogService = externalCatalogService;
    }

    @Transactional
    @Override
    public ProductDiscount handle(CreateProductDiscountCommand command) {

        if (!externalCatalogService.existsProductVariantById(command.productVariantId())) {
            throw new NotFoundException("error.not_found", new String[]{command.productVariantId().toString()}, "productVariantId");
        }

        var discount = discountDomainService.getDiscountByTenantAndId(command.tenantId(), command.discountId());

        return productDiscountDomainService.createProductDiscount(
                command.tenantId(),
                command.productVariantId(),
                discount
        );

    }
}
