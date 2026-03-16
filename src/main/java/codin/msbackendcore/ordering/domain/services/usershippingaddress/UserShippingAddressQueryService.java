package codin.msbackendcore.ordering.domain.services.usershippingaddress;

import codin.msbackendcore.ordering.domain.model.entities.UserShippingAddress;
import codin.msbackendcore.ordering.domain.model.queries.GetUserShippingAddressByUserQuery;

import java.util.List;

public interface UserShippingAddressQueryService {
    List<UserShippingAddress> handle(GetUserShippingAddressByUserQuery command);
}
