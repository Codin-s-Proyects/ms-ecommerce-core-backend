package codin.msbackendcore.catalog.domain.model.entities;

import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@Table(name = "product_variants", schema = "catalog")
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariant {
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "tenant_id", nullable = false, updatable = false)
    private UUID tenantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String sku;

    @Column(columnDefinition = "text", nullable = false)
    private String name;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "attributes", columnDefinition = "jsonb", nullable = false)
    private Map<String, Object> attributes = new HashMap<>();

    @Column(name = "product_quantity", nullable = false)
    private Integer productQuantity;

    @Column(name = "reserved_quantity", nullable = false)
    private Integer reservedQuantity;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    void prePersist() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.isActive = true;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public boolean canReserve(int qty) {
        return (productQuantity - reservedQuantity) >= qty;
    }

    public void reserve(int qty) {
        if (!canReserve(qty)) {
            throw new BadRequestException("stock.insufficient", new String[]{String.valueOf(qty)}, "stock");
        }
        reservedQuantity += qty;
    }

    public void release(int qty) {
        reservedQuantity -= qty;
        if (reservedQuantity < 0) reservedQuantity = 0;
    }

    public void confirm(int qty) {
        if (reservedQuantity < qty)
            throw new BadRequestException("stock.reserved_mismatch", new String[]{String.valueOf(qty)}, "stock");

        reservedQuantity -= qty;
        productQuantity -= qty;

        if (productQuantity < 0) productQuantity = 0;
    }
}

