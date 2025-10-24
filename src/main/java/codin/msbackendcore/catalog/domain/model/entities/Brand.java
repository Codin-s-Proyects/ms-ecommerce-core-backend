package codin.msbackendcore.catalog.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "brands", schema = "catalog")
public class Brand {
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @NotBlank
    @Column(columnDefinition = "text", nullable = false)
    private String name;

    @Column(columnDefinition = "citext", unique = true, nullable = false)
    private String slug;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "logo_url", columnDefinition = "text")
    private String logoUrl;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @PrePersist
    void prePersist() {
        this.createdAt = Instant.now();
    }
}
