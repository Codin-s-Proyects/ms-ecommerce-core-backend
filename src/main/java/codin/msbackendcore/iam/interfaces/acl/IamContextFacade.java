package codin.msbackendcore.iam.interfaces.acl;

import codin.msbackendcore.iam.domain.services.UserDomainService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IamContextFacade {

    private final UserDomainService userDomainService;

    public IamContextFacade(UserDomainService userDomainService) {
        this.userDomainService = userDomainService;
    }

    public UUID getUserById(UUID userId, UUID tenantId) {
        var user = userDomainService.getUserByIdAndTenantId(userId, tenantId);

        return user.getId();
    }
}
