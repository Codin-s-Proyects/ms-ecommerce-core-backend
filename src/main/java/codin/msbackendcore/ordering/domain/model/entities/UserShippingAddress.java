package codin.msbackendcore.ordering.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@Table(name = "user_shipping_addresses", schema = "ordering")
@NoArgsConstructor
@AllArgsConstructor
public class UserShippingAddress {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @NotBlank
    @Column(name = "label", columnDefinition = "text", nullable = false)
    private String label;

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

    @Column(name = "preferred_shipping_provider", columnDefinition = "text")
    private String preferredShippingProvider;

    @Column(name = "preferred_shipping_service", columnDefinition = "text")
    private String preferredShippingService;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    void prePersist() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    void preUpdate() {
        this.updatedAt = Instant.now();
    }
}
