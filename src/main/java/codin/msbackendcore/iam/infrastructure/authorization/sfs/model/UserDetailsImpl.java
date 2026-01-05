package codin.msbackendcore.iam.infrastructure.authorization.sfs.model;

import codin.msbackendcore.iam.domain.model.entities.Credential;
import codin.msbackendcore.iam.domain.model.entities.User;
import codin.msbackendcore.iam.domain.model.valueobjects.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
public class UserDetailsImpl implements UserDetails {

    private final UUID id;
    private final UUID tenantId;
    private final String username;
    @JsonIgnore
    private final String password;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(UUID id, UUID tenantId, String username, String password,
                           Collection<? extends GrantedAuthority> authorities,
                           boolean enabled) {
        this.id = id;
        this.tenantId = tenantId;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = enabled;
    }

    public static UserDetailsImpl build(User user) {

        // TODO: COLOCAR UNA EXCEPCION PERSONALIZADA
        Credential primaryCredential = user.getCredentials().stream()
                .filter(Credential::isPrimary)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("User has no primary credential"));

        List<String> roles = user.getUserRoles() == null ? List.of()
                : user.getUserRoles().stream()
                .map(ur -> ur.getRole().getCode())
                .collect(Collectors.toList());

        var authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getTenantId(),
                primaryCredential.getIdentifier(),
                primaryCredential.getSecretHash(),
                authorities,
                user.getStatus() == UserStatus.ACTIVE
        );
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}