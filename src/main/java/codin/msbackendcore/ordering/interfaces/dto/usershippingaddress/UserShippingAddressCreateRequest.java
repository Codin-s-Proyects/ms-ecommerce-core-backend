package codin.msbackendcore.ordering.interfaces.dto.usershippingaddress;

import codin.msbackendcore.ordering.domain.model.commands.usershippingaddress.CreateUserShippingAddressCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserShippingAddressCreateRequest(

        @NotNull
        UUID userId,
        @NotBlank
        String label,
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
        String preferredShippingProvider,
        String preferredShippingService,
        Boolean isDefault

) {

    public CreateUserShippingAddressCommand toCommand() {
        return new CreateUserShippingAddressCommand(
                userId,
                label,
                department,
                province,
                district,
                addressLine,
                reference,
                latitude,
                longitude,
                preferredShippingProvider,
                preferredShippingService,
                isDefault
        );
    }
}