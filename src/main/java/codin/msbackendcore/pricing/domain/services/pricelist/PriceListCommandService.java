package codin.msbackendcore.pricing.domain.services.pricelist;

import codin.msbackendcore.pricing.domain.model.commands.pricelist.CreatePriceListCommand;
import codin.msbackendcore.pricing.domain.model.commands.pricelist.DeletePriceListCommand;
import codin.msbackendcore.pricing.domain.model.commands.pricelist.UpdatePriceListCommand;
import codin.msbackendcore.pricing.domain.model.entities.PriceList;

public interface PriceListCommandService {
    PriceList handle(CreatePriceListCommand command);
    PriceList handle(UpdatePriceListCommand command);
    void handle(DeletePriceListCommand command);
}
