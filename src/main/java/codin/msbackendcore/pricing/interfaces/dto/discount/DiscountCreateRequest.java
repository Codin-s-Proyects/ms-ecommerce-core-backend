package codin.msbackendcore.pricing.interfaces.dto.discount;

import codin.msbackendcore.pricing.domain.model.commands.CreateDiscountCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record DiscountCreateRequest(
        @NotNull UUID tenantId,
        @NotBlank String name,
        @NotBlank String description,
        @NotNull BigDecimal percentage,
        @NotNull Instant startsAt,
        @NotNull Instant endsAt
) {
    public CreateDiscountCommand toCommand() {
        return new CreateDiscountCommand(
                tenantId,
                name,
                description,
                percentage,
                startsAt,
                endsAt
        );
    }
}
