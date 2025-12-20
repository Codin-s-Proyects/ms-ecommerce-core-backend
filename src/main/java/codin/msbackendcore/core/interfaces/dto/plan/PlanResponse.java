package codin.msbackendcore.core.interfaces.dto.plan;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PlanResponse(
        UUID id,
        UUID tenantId,
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
