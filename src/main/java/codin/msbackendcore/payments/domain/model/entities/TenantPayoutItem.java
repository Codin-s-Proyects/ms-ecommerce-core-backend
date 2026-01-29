package codin.msbackendcore.payments.domain.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@Table(name = "tenant_payout_items", schema = "payment")
@NoArgsConstructor
@AllArgsConstructor
public class TenantPayoutItem {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_payout_id", nullable = false)
    private TenantPayout tenantPayoutId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_commission_id", nullable = false)
    private SaleCommission saleCommission;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
}
