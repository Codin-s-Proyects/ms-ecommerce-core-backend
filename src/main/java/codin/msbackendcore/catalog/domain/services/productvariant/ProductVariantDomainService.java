package codin.msbackendcore.catalog.domain.services.productvariant;

import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.model.entities.ProductVariant;

import java.util.Map;
import java.util.UUID;

public interface ProductVariantDomainService {
    ProductVariant createProductVariant(UUID tenantId, Product product, String name, Map<String, Object> attributes, String imageUrl);
}
