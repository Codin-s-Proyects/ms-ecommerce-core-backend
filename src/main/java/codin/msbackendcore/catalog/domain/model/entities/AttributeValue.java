package codin.msbackendcore.catalog.domain.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@Table(name = "attribute_values", schema = "catalog")
@NoArgsConstructor
@AllArgsConstructor
public class AttributeValue {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_id", nullable = false)
    private Attribute attribute;

    @Column(columnDefinition = "text", nullable = false, unique = true)
    private String value;

    @Column(columnDefinition = "text")
    private String label;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;


    @PrePersist
    void prePersist() {
        this.createdAt = Instant.now();
    }
}
