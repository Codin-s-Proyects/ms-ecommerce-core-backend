package codin.msbackendcore.ordering.application.internal.commandservice;

import codin.msbackendcore.ordering.application.internal.outboundservices.ExternalCoreService;
import codin.msbackendcore.ordering.application.internal.outboundservices.ExternalIamService;
import codin.msbackendcore.ordering.domain.model.commands.usershippingaddress.CreateUserShippingAddressCommand;
import codin.msbackendcore.ordering.domain.model.entities.UserShippingAddress;
import codin.msbackendcore.ordering.domain.services.usershippingaddress.UserShippingAddressCommandService;
import codin.msbackendcore.ordering.domain.services.usershippingaddress.UserShippingAddressDomainService;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class UserShippingAddressCommandServiceImpl implements UserShippingAddressCommandService {

    private final UserShippingAddressDomainService userShippingAddressDomainService;
    private final ExternalCoreService externalCoreService;
    private final ExternalIamService externalIamService;

    public UserShippingAddressCommandServiceImpl(UserShippingAddressDomainService userShippingAddressDomainService, ExternalCoreService externalCoreService, ExternalIamService externalIamService) {
        this.userShippingAddressDomainService = userShippingAddressDomainService;
        this.externalCoreService = externalCoreService;
        this.externalIamService = externalIamService;
    }

    @Override
    public UserShippingAddress handle(CreateUserShippingAddressCommand command) {

        if (!externalIamService.existsUserById(command.userId())) {
            throw new BadRequestException("error.bad_request", new String[]{command.userId().toString()}, "userId");
        }

        return userShippingAddressDomainService.createUserShippingAddress(
                command.userId(),
                command.label(),
                command.department(),
                command.province(),
                command.district(),
                command.addressLine(),
                command.reference(),
                command.latitude(),
                command.longitude(),
                command.preferredShippingProvider(),
                command.preferredShippingService(),
                command.isDefault()
        );
    }
}
