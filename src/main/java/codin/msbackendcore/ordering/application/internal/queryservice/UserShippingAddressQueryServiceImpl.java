package codin.msbackendcore.ordering.application.internal.queryservice;

import codin.msbackendcore.ordering.domain.model.entities.UserShippingAddress;
import codin.msbackendcore.ordering.domain.model.queries.GetUserShippingAddressByUserQuery;
import codin.msbackendcore.ordering.domain.services.usershippingaddress.UserShippingAddressDomainService;
import codin.msbackendcore.ordering.domain.services.usershippingaddress.UserShippingAddressQueryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserShippingAddressQueryServiceImpl implements UserShippingAddressQueryService {

    private final UserShippingAddressDomainService userShippingAddressDomainService;

    public UserShippingAddressQueryServiceImpl(UserShippingAddressDomainService userShippingAddressDomainService) {
        this.userShippingAddressDomainService = userShippingAddressDomainService;
    }

    @Override
    public List<UserShippingAddress> handle(GetUserShippingAddressByUserQuery query) {
        return userShippingAddressDomainService.getUserShippingAddressesByUser(query.userId());
    }
}
