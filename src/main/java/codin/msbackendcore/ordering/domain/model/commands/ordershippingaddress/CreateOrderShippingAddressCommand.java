package codin.msbackendcore.ordering.domain.model.commands.ordershippingaddress;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

public record CreateOrderShippingAddressCommand(
        String department,
        String province,
        String district,
        String addressLine,
        String reference,
        Double latitude,
        Double longitude,
        String shippingProvider,
        String shippingService,
        BigDecimal shippingCost,
        Map<String, Object> providerMetadata,
        Instant shippedAt
) {
}
