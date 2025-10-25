package codin.msbackendcore.pricing.domain.services.productdiscount;

import codin.msbackendcore.pricing.domain.model.commands.CreateProductDiscountCommand;
import codin.msbackendcore.pricing.domain.model.entities.ProductDiscount;

public interface ProductDiscountCommandService {
    ProductDiscount handle(CreateProductDiscountCommand command);
}
