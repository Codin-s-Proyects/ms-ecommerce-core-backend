package codin.msbackendcore.ordering.domain.services.usershippingaddress;

import codin.msbackendcore.ordering.domain.model.commands.usershippingaddress.CreateUserShippingAddressCommand;
import codin.msbackendcore.ordering.domain.model.entities.UserShippingAddress;

public interface UserShippingAddressCommandService {
    UserShippingAddress handle(CreateUserShippingAddressCommand command);
}
