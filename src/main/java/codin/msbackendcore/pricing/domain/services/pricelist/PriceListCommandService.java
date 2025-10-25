package codin.msbackendcore.pricing.domain.services.pricelist;

import codin.msbackendcore.pricing.domain.model.commands.CreatePriceListCommand;
import codin.msbackendcore.pricing.domain.model.entities.PriceList;

public interface PriceListCommandService {
    PriceList handle(CreatePriceListCommand command);
}
