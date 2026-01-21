package codin.msbackendcore.ordering.interfaces.dto.order.response;

public record OrderShippingAddressResponse(
        String department,
        String province,
        String district,
        String addressLine,
        String reference
) {
}