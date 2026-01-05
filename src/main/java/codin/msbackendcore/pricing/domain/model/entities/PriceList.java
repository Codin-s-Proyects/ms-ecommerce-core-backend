package codin.msbackendcore.pricing.domain.model.entities;

import codin.msbackendcore.pricing.domain.model.valueobjects.PriceListStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

import static codin.msbackendcore.shared.infrastructure.utils.Constants.DEFAULT_CURRENCY_CODE;

@Builder
@Entity
@Table(name = "price_lists", schema = "pricing")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceList {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(columnDefinition = "TEXT", nullable = false, unique = true)
    private String code;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "currency_code", columnDefinition = "TEXT", nullable = false)
    private String currencyCode;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "status", nullable = false)
    private PriceListStatus status;

    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = false)
    private Instant validTo;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, updatable = false)
    private Instant updatedAt;

    @PrePersist
    void prePersist() {
        if (this.currencyCode == null) this.currencyCode = DEFAULT_CURRENCY_CODE;
        this.status = PriceListStatus.ACTIVE;
        this.validFrom = Instant.now();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = Instant.now();
    }
}

