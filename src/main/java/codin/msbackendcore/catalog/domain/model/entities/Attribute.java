package codin.msbackendcore.catalog.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "attributes", schema = "catalog")
public class Attribute {
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @NotBlank
    @Column(columnDefinition = "text", nullable = false, unique = true)
    private String code;

    @NotBlank
    @Column(columnDefinition = "text", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "data_type", columnDefinition = "text", nullable = false)
    private String dataType;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;


    @PrePersist
    void prePersist() {
        this.createdAt = Instant.now();
    }
}
