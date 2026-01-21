package codin.msbackendcore.ordering.interfaces.dto.order.request;

import codin.msbackendcore.ordering.domain.model.commands.ordershippingaddress.CreateOrderShippingAddressCommand;
import jakarta.validation.constraints.NotBlank;

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
        Double longitude

) {

    public CreateOrderShippingAddressCommand toCommand() {
        return new CreateOrderShippingAddressCommand(
                department,
                province,
                district,
                addressLine,
                reference,
                latitude,
                longitude
        );
    }
}