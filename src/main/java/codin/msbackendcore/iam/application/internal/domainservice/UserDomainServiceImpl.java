package codin.msbackendcore.iam.application.internal.domainservice;

import codin.msbackendcore.iam.application.internal.outboundservices.hashing.HashingService;
import codin.msbackendcore.iam.domain.model.commands.SignUpCommand;
import codin.msbackendcore.iam.domain.model.entities.Credential;
import codin.msbackendcore.iam.domain.model.entities.Role;
import codin.msbackendcore.iam.domain.model.entities.User;
import codin.msbackendcore.iam.domain.model.entities.UserRole;
import codin.msbackendcore.iam.domain.services.UserDomainService;
import codin.msbackendcore.iam.infrastructure.persistence.jpa.RoleRepository;
import codin.msbackendcore.iam.infrastructure.persistence.jpa.UserRepository;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class UserDomainServiceImpl implements UserDomainService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final RoleRepository roleRepository;

    public UserDomainServiceImpl(UserRepository userRepository, HashingService hashingService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.roleRepository = roleRepository;
    }

    @Override
    public User registerNewUser(SignUpCommand command, UUID systemUserId) {
        User user = new User();
        user.setTenantId(command.tenantId());
        user.setUserType(command.userType());

        Credential credential = new Credential();
        credential.setUser(user);
        credential.setType(command.type());
        credential.setIdentifier(command.identifier());
        credential.setSecretHash(hashingService.encode(command.secretHash()));
        credential.setPrimary(true);

        Role role = roleRepository.findByTenantIdAndCode(command.tenantId(), command.role())
                .orElseThrow(() -> new NotFoundException("error.not_found", new String[]{command.role()}, "role"));

        UserRole userRole = new UserRole();
        userRole.setAssignedBy(systemUserId);
        userRole.setUser(user);
        userRole.setRole(role);

        user.getCredentials().add(credential);
        user.getUserRoles().add(userRole);

        return userRepository.save(user);
    }

    @Override
    public boolean isPasswordValid(Credential credential, String rawPassword) {
        if (credential.getSecretHash() == null) return false;
        return hashingService.matches(rawPassword, credential.getSecretHash());
    }

    @Override
    public void updateUserLastLogin(User user) {
        user.setLastLogin(Instant.now());
        userRepository.save(user);
    }
}
