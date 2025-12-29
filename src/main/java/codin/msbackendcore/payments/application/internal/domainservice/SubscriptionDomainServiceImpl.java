package codin.msbackendcore.payments.application.internal.domainservice;

import codin.msbackendcore.payments.domain.model.entities.Subscription;
import codin.msbackendcore.payments.domain.model.valueobjects.SubscriptionStatus;
import codin.msbackendcore.payments.domain.services.subscription.SubscriptionDomainService;
import codin.msbackendcore.payments.infrastructure.persistence.jpa.SubscriptionRepository;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class SubscriptionDomainServiceImpl implements SubscriptionDomainService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionDomainServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Subscription createSubscription(UUID tenantId, UUID planId, SubscriptionStatus status, Instant startedAt, Instant nextBillingAt) {

        if (subscriptionRepository.findByTenantId(tenantId).isPresent()) {
            throw new BadRequestException(
                    "error.already_exist",
                    new String[]{tenantId.toString()},
                    "tenantId"
            );
        }

        if (nextBillingAt.isBefore(startedAt)) {
            throw new BadRequestException(
                    "error.bad_request",
                    new String[]{nextBillingAt.toString()},
                    "nextBillingAt"
            );
        }

        var subscription = Subscription.builder()
                .tenantId(tenantId)
                .planId(planId)
                .status(status)
                .startedAt(startedAt)
                .nextBillingAt(nextBillingAt)
                .build();

        return subscriptionRepository.save(subscription);
    }
}
