package codin.msbackendcore.ordering.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@Table(name = "order_shipping_addresses", schema = "ordering")
@NoArgsConstructor
@AllArgsConstructor
public class OrderShippingAddress {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @NotBlank
    @Column(name = "department", columnDefinition = "text", nullable = false)
    private String department;

    @NotBlank
    @Column(name = "province", columnDefinition = "text", nullable = false)
    private String province;

    @NotBlank
    @Column(name = "district", columnDefinition = "text", nullable = false)
    private String district;

    @NotBlank
    @Column(name = "address_line", columnDefinition = "text", nullable = false)
    private String addressLine;

    @Column(name = "reference", columnDefinition = "text")
    private String reference;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @PrePersist
    void prePersist() {
        this.createdAt = Instant.now();
    }
}
