package codin.msbackendcore.catalog.domain.services.productvariant;

import codin.msbackendcore.catalog.domain.model.commands.productvariant.CreateProductVariantBulkCommand;
import codin.msbackendcore.catalog.domain.model.commands.productvariant.CreateProductVariantCommand;
import codin.msbackendcore.catalog.domain.model.commands.productvariant.UpdateProductVariantCommand;
import codin.msbackendcore.catalog.domain.model.entities.ProductVariant;

public interface ProductVariantCommandService {
    ProductVariant handle(CreateProductVariantCommand command);
    void handle(CreateProductVariantBulkCommand command);
    ProductVariant handle(UpdateProductVariantCommand command);
}
