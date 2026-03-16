package codin.msbackendcore.ordering.interfaces.dto.usershippingaddress;

import java.time.Instant;
import java.util.UUID;

public record UserShippingAddressResponse(
        UUID id,
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
        Boolean isDefault,
        Instant createdAt,
        Instant updatedAt
) {
}