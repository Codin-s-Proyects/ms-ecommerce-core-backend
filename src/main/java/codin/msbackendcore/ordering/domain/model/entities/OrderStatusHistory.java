package codin.msbackendcore.ordering.domain.model.entities;

import codin.msbackendcore.ordering.domain.model.valueobjects.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@Table(name = "order_status_history", schema = "ordering")
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusHistory {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "text", nullable = false)
    private OrderStatus status;

    @Column(name = "changed_at", nullable = false)
    private Instant changedAt;

    @NotNull
    @Column(name = "changed_by")
    private UUID changedBy;

    @PrePersist
    protected void onCreate() {
        this.changedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdated() {
        this.changedAt = Instant.now();
    }

}
