package codin.msbackendcore.pricing.application.internal.commandservice;

import codin.msbackendcore.catalog.application.internal.outboundservices.ExternalCoreService;
import codin.msbackendcore.pricing.domain.model.commands.CreatePriceListCommand;
import codin.msbackendcore.pricing.domain.model.entities.PriceList;
import codin.msbackendcore.pricing.domain.services.pricelist.PriceListCommandService;
import codin.msbackendcore.pricing.domain.services.pricelist.PriceListDomainService;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PriceListCommandServiceImpl implements PriceListCommandService {

    private final PriceListDomainService priceListDomainService;
    private final ExternalCoreService externalCoreService;

    public PriceListCommandServiceImpl(PriceListDomainService priceListDomainService, ExternalCoreService externalCoreService) {
        this.priceListDomainService = priceListDomainService;
        this.externalCoreService = externalCoreService;
    }

    @Transactional
    @Override
    public PriceList handle(CreatePriceListCommand command) {

        if (!externalCoreService.existTenantById(command.tenantId())) {
            throw new BadRequestException("error.bad_request", new String[]{command.tenantId().toString()}, "tenantId");
        }

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
