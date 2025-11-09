package codin.msbackendcore.ordering.application.internal.outboundservices;

import codin.msbackendcore.iam.interfaces.acl.IamContextFacade;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExternalIamService {
    private final IamContextFacade iamContextFacade;

    public ExternalIamService(IamContextFacade iamContextFacade) {
        this.iamContextFacade = iamContextFacade;
    }

    public boolean existsUserById(UUID userId, UUID tenantId) {
        return iamContextFacade.getUserById(userId, tenantId) != null;
    }
}
