package codin.msbackendcore.pricing.application.internal.commandservice;

import codin.msbackendcore.catalog.application.internal.outboundservices.ExternalCoreService;
import codin.msbackendcore.pricing.domain.model.commands.CreateDiscountCommand;
import codin.msbackendcore.pricing.domain.model.entities.Discount;
import codin.msbackendcore.pricing.domain.services.discount.DiscountCommandService;
import codin.msbackendcore.pricing.domain.services.discount.DiscountDomainService;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DiscountCommandServiceImpl implements DiscountCommandService {

    private final DiscountDomainService discountDomainService;
    private final ExternalCoreService externalCoreService;

    public DiscountCommandServiceImpl(DiscountDomainService discountDomainService, ExternalCoreService externalCoreService) {
        this.discountDomainService = discountDomainService;
        this.externalCoreService = externalCoreService;
    }

    @Override
    public Discount handle(CreateDiscountCommand command) {

        if (!externalCoreService.existTenantById(command.tenantId())) {
            throw new BadRequestException("error.bad_request", new String[]{command.tenantId().toString()}, "tenantId");
        }

        return discountDomainService.createDiscount(
                command.tenantId(),
                command.name(),
                command.description(),
                command.percentage(),
                command.startsAt(),
                command.endsAt()
        );

    }
}
