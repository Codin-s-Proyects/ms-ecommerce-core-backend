package codin.msbackendcore.payments.domain.services.subscription;

import codin.msbackendcore.payments.domain.model.entities.Subscription;
import codin.msbackendcore.payments.domain.model.valueobjects.SubscriptionStatus;

import java.time.Instant;
import java.util.UUID;

public interface SubscriptionDomainService {
    Subscription createSubscription(UUID tenantId, UUID planId, SubscriptionStatus status, Instant startedAt, Instant nextBillingAt);
}
