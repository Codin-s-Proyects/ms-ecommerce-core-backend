package codin.msbackendcore.core.domain.model.entities;

import codin.msbackendcore.core.domain.model.valueobjects.TenantPlan;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tenants", schema = "core")
public class Tenant {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "slug", columnDefinition = "CITEXT", nullable = false)
    private String slug;

    @Column(name = "name", columnDefinition = "TEXT", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "plan", columnDefinition = "TEXT", nullable = false)
    private TenantPlan plan = TenantPlan.BASIC;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }
}
