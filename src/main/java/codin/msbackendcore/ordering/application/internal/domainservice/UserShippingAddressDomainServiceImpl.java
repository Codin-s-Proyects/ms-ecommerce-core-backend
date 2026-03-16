package codin.msbackendcore.ordering.application.internal.domainservice;

import codin.msbackendcore.ordering.domain.model.entities.UserShippingAddress;
import codin.msbackendcore.ordering.domain.services.usershippingaddress.UserShippingAddressDomainService;
import codin.msbackendcore.ordering.infrastructure.persistence.jpa.repositories.UserShippingAddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserShippingAddressDomainServiceImpl implements UserShippingAddressDomainService {

    private final UserShippingAddressRepository userShippingAddressRepository;

    public UserShippingAddressDomainServiceImpl(UserShippingAddressRepository userShippingAddressRepository) {
        this.userShippingAddressRepository = userShippingAddressRepository;
    }

    @Override
    public UserShippingAddress createUserShippingAddress(UUID userId, String label, String department, String province, String district, String addressLine, String reference, Double latitude, Double longitude,
                                                         String preferredShippingProvider, String preferredShippingService, Boolean isDefault) {
        var userShippingAddress = UserShippingAddress.builder()
                .userId(userId)
                .label(label)
                .department(department)
                .province(province)
                .district(district)
                .addressLine(addressLine)
                .reference(reference)
                .latitude(latitude)
                .longitude(longitude)
                .preferredShippingProvider(preferredShippingProvider)
                .preferredShippingService(preferredShippingService)
                .isDefault(isDefault)
                .build();

        return userShippingAddressRepository.save(userShippingAddress);
    }

    @Override
    public List<UserShippingAddress> getUserShippingAddressesByUser(UUID userId) {
        return userShippingAddressRepository.findAllByUserId(userId);
    }
}
