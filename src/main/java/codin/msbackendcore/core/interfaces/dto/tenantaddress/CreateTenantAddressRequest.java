package codin.msbackendcore.core.interfaces.dto.tenantaddress;

public record CreateTenantAddressRequest(
        String line1,
        String city,
        String country
) {
}
