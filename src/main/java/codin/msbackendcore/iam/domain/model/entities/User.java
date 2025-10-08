package codin.msbackendcore.iam.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users", schema = "iam")
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    @NotNull
    private UUID tenantId;

    @Column(name = "user_type", nullable = false)
    @NotBlank
    private String userType;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    @Column(name = "mfa_enabled", nullable = false)
    private boolean mfaEnabled = false;

    @Lob
    private byte[] mfaSecret;

    private Instant lastLogin;

    @Column(columnDefinition = "jsonb", nullable = false)
    @NotNull
    private String meta = "{}";

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Credential> credentials = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserRole> userRoles = new ArrayList<>();

    @PrePersist
    void prePersist() {
        this.createdAt = Instant.now();
    }


    public List<String> getRoleCodes() {
        if (userRoles == null || userRoles.isEmpty()) return List.of();
        return userRoles.stream()
                .map(userRole -> userRole.getRole().getCode())
                .toList();
    }

    public List<String> getRoleNames() {
        if (userRoles == null || userRoles.isEmpty()) return List.of();
        return userRoles.stream()
                .map(userRole -> userRole.getRole().getName())
                .toList();
    }
}
