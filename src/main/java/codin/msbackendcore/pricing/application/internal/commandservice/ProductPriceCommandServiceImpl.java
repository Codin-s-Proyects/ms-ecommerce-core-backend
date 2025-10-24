package codin.msbackendcore.pricing.application.internal.commandservice;

import codin.msbackendcore.pricing.domain.model.commands.CreateProductPriceCommand;
import codin.msbackendcore.pricing.domain.model.entities.ProductPrice;
import codin.msbackendcore.pricing.domain.services.pricinglist.PriceListDomainService;
import codin.msbackendcore.pricing.domain.services.productprice.ProductPriceCommandService;
import codin.msbackendcore.pricing.domain.services.productprice.ProductPriceDomainService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProductPriceCommandServiceImpl implements ProductPriceCommandService {

    private final ProductPriceDomainService productPriceDomainService;
    private final PriceListDomainService priceListDomainService;

    public ProductPriceCommandServiceImpl(ProductPriceDomainService productPriceDomainService, PriceListDomainService priceListDomainService) {
        this.productPriceDomainService = productPriceDomainService;
        this.priceListDomainService = priceListDomainService;
    }

    @Transactional
    @Override
    public ProductPrice handle(CreateProductPriceCommand command) {

        var priceList = priceListDomainService.getPriceListByTenantAndId(command.tenantId(), command.priceListId());

        return productPriceDomainService.createProductPrice(
                command.tenantId(),
                command.productVariantId(),
                priceList,
                command.basePrice(),
                command.minQuantity(),
                command.validTo()
        );

    }
}
