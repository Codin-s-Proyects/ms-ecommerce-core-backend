package codin.msbackendcore.iam.domain.services.role;

import codin.msbackendcore.iam.domain.model.entities.Role;

public interface RoleDomainService {
    Role findByRole(String code);
}
