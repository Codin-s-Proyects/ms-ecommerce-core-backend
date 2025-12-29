package codin.msbackendcore.payments.interfaces.dto.subscription;

import codin.msbackendcore.payments.domain.model.commands.subscription.CreateSubscriptionCommand;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public record SubscriptionCreateRequest(
        @NotNull UUID tenantId,
        @NotNull UUID planId,
        @NotNull String status,
        @NotNull Instant startedAt,
        @NotNull Instant nextBillingAt
) {
    public CreateSubscriptionCommand toCommand() {
        return new CreateSubscriptionCommand(
                this.tenantId,
                this.planId,
                this.status,
                this.startedAt,
                this.nextBillingAt
        );
    }
}
