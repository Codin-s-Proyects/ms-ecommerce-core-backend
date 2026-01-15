package codin.msbackendcore.payments.application.internal.commandservice;

import codin.msbackendcore.payments.application.internal.outboundservices.ExternalCoreService;
import codin.msbackendcore.payments.domain.model.commands.subscription.CreateSubscriptionCommand;
import codin.msbackendcore.payments.domain.model.entities.Subscription;
import codin.msbackendcore.payments.domain.model.valueobjects.SubscriptionStatus;
import codin.msbackendcore.payments.domain.services.subscription.SubscriptionCommandService;
import codin.msbackendcore.payments.domain.services.subscription.SubscriptionDomainService;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.isValidEnum;

@Service
public class SubscriptionCommandServiceImpl implements SubscriptionCommandService {
    private final ExternalCoreService externalCoreService;
    private final SubscriptionDomainService subscriptionDomainService;

    public SubscriptionCommandServiceImpl(ExternalCoreService externalCoreService, SubscriptionDomainService subscriptionDomainService) {
        this.externalCoreService = externalCoreService;
        this.subscriptionDomainService = subscriptionDomainService;
    }

    @Override
    public Subscription handle(CreateSubscriptionCommand command) {
        if (!externalCoreService.existTenantById(command.tenantId())) {
            throw new BadRequestException("error.bad_request", new String[]{command.tenantId().toString()}, "tenantId");
        }

        if (!externalCoreService.existPlanById(command.planId())) {
            throw new BadRequestException("error.bad_request", new String[]{command.planId().toString()}, "planId");
        }

        if (!isValidEnum(SubscriptionStatus.class, command.status())) {
            throw new BadRequestException("error.bad_request", new String[]{command.status()}, "status");
        }

        return subscriptionDomainService.createSubscription(
                command.tenantId(),
                command.planId(),
                SubscriptionStatus.valueOf(command.status()),
                command.startedAt(),
                command.nextBillingAt()
        );
    }
}
