package codin.msbackendcore.iam.application.internal.domainservice;

import codin.msbackendcore.iam.domain.services.role.RoleDomainService;
import codin.msbackendcore.iam.infrastructure.persistence.jpa.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleDomainServiceImpl implements RoleDomainService {

    private final RoleRepository roleRepository;

    public RoleDomainServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public boolean existsByRole(String role) {
        return roleRepository.existsByCode(role);
    }
}
