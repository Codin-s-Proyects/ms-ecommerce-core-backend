package codin.msbackendcore.pricing.application.internal.commandservice;

import codin.msbackendcore.pricing.domain.model.commands.CreatePriceListCommand;
import codin.msbackendcore.pricing.domain.model.entities.PriceList;
import codin.msbackendcore.pricing.domain.services.pricinglist.PriceListCommandService;
import codin.msbackendcore.pricing.domain.services.pricinglist.PriceListDomainService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PriceListCommandServiceImpl implements PriceListCommandService {

    private final PriceListDomainService priceListDomainService;

    public PriceListCommandServiceImpl(PriceListDomainService priceListDomainService) {
        this.priceListDomainService = priceListDomainService;
    }

    @Transactional
    @Override
    public PriceList handle(CreatePriceListCommand command) {

        return priceListDomainService.createPriceList(
                command.tenantId(),
                command.code(),
                command.name(),
                command.description(),
                command.currencyCode(),
                command.validFrom(),
                command.validTo()
        );

    }
}
