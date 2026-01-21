package codin.msbackendcore.ordering.interfaces.dto.order.request;

import codin.msbackendcore.ordering.domain.model.commands.ordercustomer.CreateOrderCustomerCommand;
import jakarta.validation.constraints.NotBlank;

public record OrderCustomerCreateRequest(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        String email,
        @NotBlank
        String phone,
        @NotBlank
        String documentType,
        @NotBlank
        String documentNumber
) {

    public CreateOrderCustomerCommand toCommand() {
        return new CreateOrderCustomerCommand(
                firstName,
                lastName,
                email,
                phone,
                documentType,
                documentNumber
        );
    }
}