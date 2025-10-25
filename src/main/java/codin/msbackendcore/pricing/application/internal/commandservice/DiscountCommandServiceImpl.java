package codin.msbackendcore.pricing.application.internal.commandservice;

import codin.msbackendcore.pricing.domain.model.commands.CreateDiscountCommand;
import codin.msbackendcore.pricing.domain.model.entities.Discount;
import codin.msbackendcore.pricing.domain.services.discount.DiscountCommandService;
import codin.msbackendcore.pricing.domain.services.discount.DiscountDomainService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DiscountCommandServiceImpl implements DiscountCommandService {

    private final DiscountDomainService discountDomainService;

    public DiscountCommandServiceImpl(DiscountDomainService discountDomainService) {
        this.discountDomainService = discountDomainService;
    }

    @Transactional
    @Override
    public Discount handle(CreateDiscountCommand command) {

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
