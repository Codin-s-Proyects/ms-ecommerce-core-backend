package codin.msbackendcore.pricing.domain.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
@Entity
@Table(name = "product_prices", schema = "pricing")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPrice {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "product_variant_id", nullable = false)
    private UUID productVariantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "price_list_id", nullable = false)
    private PriceList priceList;

    @Column(name = "base_price", nullable = false)
    private BigDecimal basePrice;

    @Column(name = "discount_percent", nullable = false)
    private BigDecimal discountPercent = BigDecimal.ZERO;

    @Column(name = "final_price", insertable = false, updatable = false)
    private BigDecimal finalPrice;

    @Column(name = "min_quantity")
    private Integer minQuantity = 1;

    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to")
    private Instant validTo;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, updatable = false)
    private Instant updatedAt;

    @PrePersist
    void prePersist() {
        this.validFrom = Instant.now();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
}
