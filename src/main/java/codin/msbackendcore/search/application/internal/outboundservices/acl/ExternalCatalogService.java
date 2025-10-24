package codin.msbackendcore.search.application.internal.outboundservices.acl;

import codin.msbackendcore.catalog.interfaces.acl.CatalogContextFacade;
import codin.msbackendcore.search.application.internal.dto.ProductDto;
import codin.msbackendcore.search.application.internal.dto.ProductVariantDto;
import codin.msbackendcore.search.application.internal.dto.SemanticSearchDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExternalCatalogService {
    private final CatalogContextFacade catalogContextFacade;

    public ExternalCatalogService(CatalogContextFacade catalogContextFacade) {
        this.catalogContextFacade = catalogContextFacade;
    }

    public SemanticSearchDto getSemanticSearchData(UUID variantId) {

        var productVariantResponse = catalogContextFacade.getProductVariantById(variantId);
        var productVariant = new ProductVariantDto(
                productVariantResponse.id(),
                productVariantResponse.tenantId(),
                productVariantResponse.sku(),
                productVariantResponse.name(),
                productVariantResponse.attributes(),
                productVariantResponse.imageUrl()
        );

        var productResponse = catalogContextFacade.getProductById(productVariantResponse.productId());
        var product = new ProductDto(
                productResponse.id(),
                productResponse.tenantId(),
                productResponse.name(),
                productResponse.slug(),
                productResponse.description(),
                productResponse.hasVariants()
        );

        return new SemanticSearchDto(product, productVariant);
    }
}
