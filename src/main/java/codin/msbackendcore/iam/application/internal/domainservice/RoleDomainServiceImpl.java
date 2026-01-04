package codin.msbackendcore.iam.application.internal.domainservice;

import codin.msbackendcore.iam.domain.model.entities.Role;
import codin.msbackendcore.iam.domain.services.role.RoleDomainService;
import codin.msbackendcore.iam.infrastructure.persistence.jpa.RoleRepository;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RoleDomainServiceImpl implements RoleDomainService {

    private final RoleRepository roleRepository;

    public RoleDomainServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByRole(String code) {
        return roleRepository.findRoleByCode(code)
                .orElseThrow(
                        () -> new NotFoundException("error.not_found", new String[]{code}, "code"));
    }
}
