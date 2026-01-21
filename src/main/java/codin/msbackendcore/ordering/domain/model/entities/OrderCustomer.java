package codin.msbackendcore.ordering.domain.model.entities;

import codin.msbackendcore.ordering.domain.model.valueobjects.DocumentType;
import codin.msbackendcore.ordering.domain.model.valueobjects.OrderChannel;
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
@Table(name = "order_customers", schema = "ordering")
@NoArgsConstructor
@AllArgsConstructor
public class OrderCustomer {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @NotBlank
    @Column(name = "first_name", columnDefinition = "text", nullable = false)
    private String firstName;

    @NotBlank
    @Column(name = "last_name", columnDefinition = "text", nullable = false)
    private String lastName;

    @NotBlank
    @Column(name = "email", columnDefinition = "text", nullable = false)
    private String email;

    @NotBlank
    @Column(name = "phone", columnDefinition = "text", nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", columnDefinition = "text", nullable = false)
    private DocumentType documentType;

    @NotBlank
    @Column(name = "document_number", columnDefinition = "text", nullable = false)
    private String documentNumber;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @PrePersist
    void prePersist() {
        this.createdAt = Instant.now();
    }
}
