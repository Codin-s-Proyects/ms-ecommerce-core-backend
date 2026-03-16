package codin.msbackendcore.ordering.domain.model.commands.usershippingaddress;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public record CreateUserShippingAddressCommand(
        UUID userId,
        String label,
        String department,
        String province,
        String district,
        String addressLine,
        String reference,
        Double latitude,
        Double longitude,
        String preferredShippingProvider,
        String preferredShippingService,
        Boolean isDefault
) {
}
