package codin.msbackendcore.search.application.internal.outboundservices.acl;

import codin.msbackendcore.catalog.interfaces.acl.CatalogContextFacade;
import codin.msbackendcore.core.domain.model.valueobjects.EntityType;
import codin.msbackendcore.search.application.internal.dto.ProductDto;
import codin.msbackendcore.search.application.internal.dto.ProductVariantDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("ExternalCatalogServiceForSearch")
public class ExternalCatalogService {
    private final CatalogContextFacade catalogContextFacade;
    private final ExternalCoreService externalCoreService;

    public ExternalCatalogService(CatalogContextFacade catalogContextFacade, ExternalCoreService externalCoreService) {
        this.catalogContextFacade = catalogContextFacade;
        this.externalCoreService = externalCoreService;
    }

    public ProductDto getProductById(UUID productId) {

        var productResponse = catalogContextFacade.getProductById(productId);

        var mediaAssets = externalCoreService.getMediaAssetsByEntityIdAndEntityType(
                productResponse.tenantId(),
                productResponse.id(),
                EntityType.PRODUCT
        );

        return new ProductDto(
                productResponse.id(),
                productResponse.tenantId(),
                productResponse.name(),
                productResponse.slug(),
                productResponse.description(),
                productResponse.hasVariants(),
                mediaAssets
        );
    }

    public ProductVariantDto getVariantById(UUID variantId) {

        var productVariantResponse = catalogContextFacade.getProductVariantById(variantId);

        var mediaAssets = externalCoreService.getMediaAssetsByEntityIdAndEntityType(
                productVariantResponse.tenantId(),
                productVariantResponse.id(),
                EntityType.PRODUCT
        );

        return new ProductVariantDto(
                productVariantResponse.id(),
                productVariantResponse.productId(),
                productVariantResponse.tenantId(),
                productVariantResponse.sku(),
                productVariantResponse.name(),
                productVariantResponse.attributes(),
                productVariantResponse.productQuantity(),
                mediaAssets
        );
    }
}
