package codin.msbackendcore.pricing.domain.services.discount;

import codin.msbackendcore.pricing.domain.model.commands.CreateDiscountCommand;
import codin.msbackendcore.pricing.domain.model.entities.Discount;

public interface DiscountCommandService {
    Discount handle(CreateDiscountCommand command);
}
