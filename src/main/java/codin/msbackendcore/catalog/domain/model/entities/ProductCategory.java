package codin.msbackendcore.catalog.domain.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@Table(name = "product_categories", schema = "catalog",
        uniqueConstraints = @UniqueConstraint(columnNames = {"tenant_id", "product_id", "category_id"}))
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;


    @PrePersist
    void prePersist() {
        this.createdAt = Instant.now();
    }
}
