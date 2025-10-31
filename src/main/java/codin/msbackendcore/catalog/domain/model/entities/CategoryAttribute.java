package codin.msbackendcore.catalog.domain.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Entity
@Table(name = "category_attributes", schema = "catalog")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryAttribute {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_id", nullable = false)
    private Attribute attribute;

    @Column(name = "is_variant_attribute", nullable = false)
    private Boolean isVariantAttribute;

    @PrePersist
    void prePersist() {
        this.isVariantAttribute = true;
    }
}

