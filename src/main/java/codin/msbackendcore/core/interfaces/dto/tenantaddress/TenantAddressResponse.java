package codin.msbackendcore.core.interfaces.dto.tenantaddress;

import java.util.UUID;

public record TenantAddressResponse(
        UUID id,
        String line1,
        String city,
        String country
) {
}
