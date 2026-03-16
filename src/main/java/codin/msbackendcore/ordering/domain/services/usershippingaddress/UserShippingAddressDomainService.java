package codin.msbackendcore.ordering.domain.services.usershippingaddress;

import codin.msbackendcore.ordering.domain.model.entities.UserShippingAddress;

import java.util.List;
import java.util.UUID;

public interface UserShippingAddressDomainService {
    UserShippingAddress createUserShippingAddress(UUID userId, String label, String department, String province, String district, String addressLine, String reference, Double latitude, Double longitude,
                                                  String preferredShippingProvider, String preferredShippingService, Boolean isDefault);

    List<UserShippingAddress> getUserShippingAddressesByUser(UUID userId);
}