package codin.msbackendcore.payments.application.internal.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PlanDto(
        UUID id,
        String name,
        String description,
        BigDecimal commissionRate,
        BigDecimal monthlyFee,
        BigDecimal onboardingFee,
        BigDecimal gmvMin,
        BigDecimal gmvMax,
        String status,
        Instant createdAt
) {
}
