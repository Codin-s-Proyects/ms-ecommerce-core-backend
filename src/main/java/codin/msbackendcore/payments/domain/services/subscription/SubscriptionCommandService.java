package codin.msbackendcore.payments.domain.services.subscription;

import codin.msbackendcore.payments.domain.model.commands.subscription.CreateSubscriptionCommand;
import codin.msbackendcore.payments.domain.model.entities.Subscription;

public interface SubscriptionCommandService {
    Subscription handle(CreateSubscriptionCommand command);
}
