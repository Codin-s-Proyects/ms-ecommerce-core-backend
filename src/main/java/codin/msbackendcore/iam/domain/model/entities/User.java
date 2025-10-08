package codin.msbackendcore.iam.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashMap;
import java.util.Map;

import java.time.Instant;
import java.util.*;

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

    @Column(name = "mfa_secret", columnDefinition = "bytea")
    private byte[] mfaSecret;

    private Instant lastLogin;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "meta", columnDefinition = "jsonb", nullable = false)
    private Map<String, Object> meta = new HashMap<>();

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
