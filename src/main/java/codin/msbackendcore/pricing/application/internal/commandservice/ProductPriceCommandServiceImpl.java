package codin.msbackendcore.pricing.application.internal.commandservice;

import codin.msbackendcore.pricing.application.internal.outboundservices.ExternalCatalogService;
import codin.msbackendcore.pricing.domain.model.commands.CreateProductPriceCommand;
import codin.msbackendcore.pricing.domain.model.entities.ProductPrice;
import codin.msbackendcore.pricing.domain.services.pricelist.PriceListDomainService;
import codin.msbackendcore.pricing.domain.services.productprice.ProductPriceCommandService;
import codin.msbackendcore.pricing.domain.services.productprice.ProductPriceDomainService;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProductPriceCommandServiceImpl implements ProductPriceCommandService {

    private final ProductPriceDomainService productPriceDomainService;
    private final PriceListDomainService priceListDomainService;
    private final ExternalCatalogService externalCatalogService;

    public ProductPriceCommandServiceImpl(ProductPriceDomainService productPriceDomainService, PriceListDomainService priceListDomainService, ExternalCatalogService externalCatalogService) {
        this.productPriceDomainService = productPriceDomainService;
        this.priceListDomainService = priceListDomainService;
        this.externalCatalogService = externalCatalogService;
    }

    @Transactional
    @Override
    public ProductPrice handle(CreateProductPriceCommand command) {

        if (!externalCatalogService.existsProductVariantById(command.productVariantId())) {
            throw new NotFoundException("error.not_found", new String[]{command.productVariantId().toString()}, "productVariantId");
        }

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
