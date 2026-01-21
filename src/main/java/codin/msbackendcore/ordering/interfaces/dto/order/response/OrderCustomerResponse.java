package codin.msbackendcore.ordering.interfaces.dto.order.response;

public record OrderCustomerResponse(
        String firstName,
        String lastName,
        String email,
        String phone,
        String documentType,
        String documentNumber
) {
}
