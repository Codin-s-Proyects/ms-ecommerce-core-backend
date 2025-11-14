package codin.msbackendcore.catalog.interfaces.acl;

import codin.msbackendcore.catalog.domain.services.product.ProductDomainService;
import codin.msbackendcore.catalog.domain.services.productvariant.ProductVariantDomainService;
import codin.msbackendcore.catalog.interfaces.dto.product.ProductResponse;
import codin.msbackendcore.catalog.interfaces.dto.productvariant.ProductVariantResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CatalogContextFacade {

    private final ProductDomainService productDomainService;
    private final ProductVariantDomainService productVariantDomainService;

    public CatalogContextFacade(ProductDomainService productDomainService, ProductVariantDomainService productVariantDomainService) {
        this.productDomainService = productDomainService;
        this.productVariantDomainService = productVariantDomainService;
    }

    public ProductResponse getProductById(UUID productId) {
        var product = productDomainService.getProductById(productId);

        return new ProductResponse(
                product.getId(),
                product.getTenantId(),
                product.getName(),
                product.getSlug(),
                product.getDescription(),
                product.isHasVariants()
        );
    }

    public ProductVariantResponse getProductVariantById(UUID productVariantId) {
        var product = productVariantDomainService.getProductVariantById(productVariantId);

        return new ProductVariantResponse(
                product.getId(),
                product.getProduct().getId(),
                product.getTenantId(),
                product.getSku(),
                product.getName(),
                product.getAttributes(),
                product.getProductQuantity()
        );
    }
}
