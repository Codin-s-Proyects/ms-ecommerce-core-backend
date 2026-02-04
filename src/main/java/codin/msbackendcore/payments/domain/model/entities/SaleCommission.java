package codin.msbackendcore.payments.domain.model.entities;

import codin.msbackendcore.payments.domain.model.valueobjects.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@Table(name = "sale_commissions", schema = "payment")
@NoArgsConstructor
@AllArgsConstructor
public class SaleCommission {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "gross_amount", nullable = false)
    private BigDecimal grossAmount;

    @Column(name = "commission_amount", nullable = false)
    private BigDecimal commissionAmount;

    @Column(name = "merchant_amount", nullable = false)
    private BigDecimal merchantAmount;

    @Column(name = "commission_rate", nullable = false)
    private BigDecimal commissionRate;

    @Column(name = "plan_id", nullable = false)
    private UUID planId;

    @Column(name = "currency_code", columnDefinition = "TEXT")
    private String currencyCode = "PEN";

    @Enumerated(EnumType.STRING)
    @Column(name = "payout_status", nullable = false)
    private PaymentStatus payoutStatus;

    @Column(name = "paid_at")
    private Instant paidAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }
}
