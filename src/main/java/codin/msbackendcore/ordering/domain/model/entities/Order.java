package codin.msbackendcore.ordering.domain.model.entities;

import codin.msbackendcore.ordering.domain.model.valueobjects.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static codin.msbackendcore.shared.infrastructure.utils.Constants.DEFAULT_CURRENCY_CODE;

@Builder
@Getter
@Setter
@Entity
@Table(name = "orders", schema = "ordering")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @NotBlank
    @Column(columnDefinition = "text", nullable = false, unique = true)
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "text", nullable = false)
    private OrderStatus status;

    @Column(name = "currency_code", columnDefinition = "TEXT", nullable = false)
    private String currencyCode;

    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal;

    @Column(name = "discount_total", nullable = false)
    private BigDecimal discountTotal;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @NotBlank
    @Column(columnDefinition = "text", nullable = false, unique = true)
    private String notes;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<OrderItem> items = new HashSet<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<OrderStatusHistory> statusHistory = new HashSet<>();


    @PrePersist
    void prePersist() {
        if (this.currencyCode == null) this.currencyCode = DEFAULT_CURRENCY_CODE;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public Order addItem(OrderItem item) {
        if (this.items == null) {
            this.items = new HashSet<>();
        }

        this.items.add(item);
        item.setOrder(this);
        return this;
    }

    public Order addStatusHistory(OrderStatusHistory statusHistory) {
        if (this.statusHistory == null) {
            this.statusHistory = new HashSet<>();
        }

        this.statusHistory.add(statusHistory);
        statusHistory.setOrder(this);
        return this;
    }
}
