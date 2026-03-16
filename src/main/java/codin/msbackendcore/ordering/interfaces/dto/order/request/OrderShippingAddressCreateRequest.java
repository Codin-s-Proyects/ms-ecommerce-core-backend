package codin.msbackendcore.ordering.interfaces.dto.order.request;

import codin.msbackendcore.ordering.domain.model.commands.ordershippingaddress.CreateOrderShippingAddressCommand;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

public record OrderShippingAddressCreateRequest(

        @NotBlank
        String department,

        @NotBlank
        String province,

        @NotBlank
        String district,

        @NotBlank
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

    public CreateOrderShippingAddressCommand toCommand() {
        return new CreateOrderShippingAddressCommand(
                department,
                province,
                district,
                addressLine,
                reference,
                latitude,
                longitude,
                shippingProvider,
                shippingService,
                shippingCost,
                providerMetadata,
                shippedAt
        );
    }
}