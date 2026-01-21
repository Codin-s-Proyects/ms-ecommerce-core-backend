package codin.msbackendcore.ordering.domain.model.commands.ordercustomer;

public record CreateOrderCustomerCommand(
        String firstName,
        String lastName,
        String email,
        String phone,
        String documentType,
        String documentNumber
) {
}
