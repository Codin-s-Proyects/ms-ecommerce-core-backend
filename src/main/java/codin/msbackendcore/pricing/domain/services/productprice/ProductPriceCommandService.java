package codin.msbackendcore.pricing.domain.services.productprice;

import codin.msbackendcore.pricing.domain.model.commands.productprice.CreateProductPriceCommand;
import codin.msbackendcore.pricing.domain.model.commands.productprice.UpdateProductPriceCommand;
import codin.msbackendcore.pricing.domain.model.entities.ProductPrice;

public interface ProductPriceCommandService {
    ProductPrice handle(CreateProductPriceCommand command);
    ProductPrice handle(UpdateProductPriceCommand command);
}
