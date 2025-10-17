package codin.msbackendcore.core.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tenant_settings", schema = "core")
public class TenantSettings {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "tenant_id", nullable = false, unique = true)
    private UUID tenantId;

    @Column(name = "image_prompt", columnDefinition = "TEXT")
    private String imagePrompt;

    @Column(name = "composer_prompt", columnDefinition = "TEXT")
    private String composerPrompt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
