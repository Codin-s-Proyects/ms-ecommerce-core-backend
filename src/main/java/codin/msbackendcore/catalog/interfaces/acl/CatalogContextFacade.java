package codin.msbackendcore.catalog.interfaces.acl;

import codin.msbackendcore.catalog.application.internal.outboundservices.ExternalCoreService;
import codin.msbackendcore.catalog.domain.services.product.ProductDomainService;
import codin.msbackendcore.catalog.domain.services.productvariant.ProductVariantDomainService;
import codin.msbackendcore.catalog.interfaces.dto.product.ProductResponse;
import codin.msbackendcore.catalog.interfaces.dto.product.ProductWithAssetsResponse;
import codin.msbackendcore.catalog.interfaces.dto.productvariant.CatalogEmbeddingResponse;
import codin.msbackendcore.catalog.interfaces.dto.productvariant.ProductVariantResponse;
import codin.msbackendcore.core.domain.model.valueobjects.EntityType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CatalogContextFacade {

    private final ProductDomainService productDomainService;
    private final ProductVariantDomainService productVariantDomainService;
    private final ExternalCoreService externalCoreService;

    public CatalogContextFacade(ProductDomainService productDomainService, ProductVariantDomainService productVariantDomainService, ExternalCoreService externalCoreService) {
        this.productDomainService = productDomainService;
        this.productVariantDomainService = productVariantDomainService;
        this.externalCoreService = externalCoreService;
    }

    public ProductWithAssetsResponse getProductById(UUID productId) {
        var product = productDomainService.getProductById(productId);

        var mediaAssets = externalCoreService.getMediaAssetByEntityIdAndEntityType(product.getTenantId(), product.getId(), EntityType.PRODUCT);

        return new ProductWithAssetsResponse(
                product.getId(),
                product.getTenantId(),
                product.getName(),
                product.getSlug(),
                product.getDescription(),
                product.isHasVariants(),
                product.getStatus().name(),
                mediaAssets
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
                product.getProductQuantity() - product.getReservedQuantity(),
                product.getStatus().name()
        );
    }

    @Transactional(readOnly = true)
    public List<CatalogEmbeddingResponse> getEmbeddingCatalogByProductId(UUID productId, UUID tenantId) {
        var product = productDomainService.getProductById(productId);
        var productVariantList = productVariantDomainService.getVariantsByProductId(product, tenantId);

        String categoryName = (product.getCategories() != null && !product.getCategories().isEmpty())
                ? product.getCategories().stream()
                .map(pc -> pc.getCategory().getName())
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" "))
                : "";

        return productVariantList.stream().map(variant -> new CatalogEmbeddingResponse(
                product.getTenantId(),
                variant.getId(),
                product.getName(),
                categoryName,
                product.getBrand().getName(),
                product.getDescription(),
                variant.getName(),
                variant.getAttributes()
        )).toList();
    }

    @Transactional
    public void reserve(UUID variantId, UUID tenantId, int qty) {

        productVariantDomainService.reserve(variantId, tenantId, qty);
    }

    @Transactional
    public void release(UUID variantId, UUID tenantId, int qty) {
        productVariantDomainService.release(variantId, tenantId, qty);
    }

    @Transactional
    public void confirm(UUID variantId, UUID tenantId, int qty) {
        productVariantDomainService.confirm(variantId, tenantId, qty);
    }
}
