package codin.msbackendcore.ordering.domain.model.commands.ordershippingaddress;

public record CreateOrderShippingAddressCommand(
        String department,
        String province,
        String district,
        String addressLine,
        String reference,
        Double latitude,
        Double longitude
) {
}
