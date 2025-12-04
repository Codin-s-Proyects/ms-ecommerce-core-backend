package codin.msbackendcore.catalog.domain.services.productvariant;

import codin.msbackendcore.catalog.domain.model.commands.productvariant.CreateProductVariantBulkCommand;
import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.model.entities.ProductVariant;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductVariantDomainService {
    ProductVariant createProductVariant(UUID tenantId, Product product, String name, Map<String, Object> attributes, Integer productQuantity);
    List<ProductVariant> createProductVariantBulk(UUID tenantId, Product product, List<CreateProductVariantBulkCommand.VariantItemCommand> variants);
    ProductVariant getProductVariantById(UUID productVariantId);
}
