package codin.msbackendcore.catalog.domain.model.entities;

import codin.msbackendcore.catalog.domain.model.valueobjects.DataType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@Table(name = "attributes", schema = "catalog")
@NoArgsConstructor
@AllArgsConstructor
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

    @Enumerated(EnumType.STRING)
    @Column(name = "data_type", columnDefinition = "text", nullable = false)
    private DataType dataType;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;


    @PrePersist
    void prePersist() {
        this.createdAt = Instant.now();
    }
}
