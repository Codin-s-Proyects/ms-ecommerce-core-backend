package codin.msbackendcore.core.domain.model.entities;


import codin.msbackendcore.core.domain.model.valueobjects.PlanStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "plans", schema = "core")
public class Plan {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "name", columnDefinition = "TEXT", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(
            name = "commission_rate",
            nullable = false,
            precision = 5,
            scale = 2
    )
    private BigDecimal commissionRate;

    @Column(
            name = "monthly_fee",
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal monthlyFee;

    @Column(
            name = "onboarding_fee",
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal onboardingFee;

    @Column(
            name = "gmv_min",
            precision = 12,
            scale = 2
    )
    private BigDecimal gmvMin;

    @Column(
            name = "gmv_max",
            precision = 12,
            scale = 2
    )
    private BigDecimal gmvMax;

    @Column(name = "status", columnDefinition = "TEXT")
    @Enumerated(EnumType.STRING)
    private PlanStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }
}
