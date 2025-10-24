package codin.msbackendcore.pricing.domain.services.productprice;

import codin.msbackendcore.pricing.domain.model.commands.CreateProductPriceCommand;
import codin.msbackendcore.pricing.domain.model.entities.ProductPrice;

public interface ProductPriceCommandService {
    ProductPrice handle(CreateProductPriceCommand command);
}
