package codin.msbackendcore.search.application.internal.outboundservices.acl;

import codin.msbackendcore.catalog.interfaces.acl.CatalogContextFacade;
import codin.msbackendcore.search.application.internal.dto.ProductDto;
import codin.msbackendcore.search.application.internal.dto.ProductVariantDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("ExternalCatalogServiceForSearch")
public class ExternalCatalogService {
    private final CatalogContextFacade catalogContextFacade;

    public ExternalCatalogService(CatalogContextFacade catalogContextFacade) {
        this.catalogContextFacade = catalogContextFacade;
    }

    public ProductDto getProductById(UUID productId) {

        var productResponse = catalogContextFacade.getProductById(productId);

        return new ProductDto(
                productResponse.id(),
                productResponse.tenantId(),
                productResponse.name(),
                productResponse.slug(),
                productResponse.description(),
                productResponse.hasVariants()
        );
    }

    public ProductVariantDto getVariantById(UUID variantId) {

        var productVariantResponse = catalogContextFacade.getProductVariantById(variantId);
        return new ProductVariantDto(
                productVariantResponse.id(),
                productVariantResponse.productId(),
                productVariantResponse.tenantId(),
                productVariantResponse.sku(),
                productVariantResponse.name(),
                productVariantResponse.attributes(),
                productVariantResponse.imageUrl()
        );
    }
}
