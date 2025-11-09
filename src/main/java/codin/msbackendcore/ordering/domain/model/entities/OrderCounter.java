package codin.msbackendcore.ordering.domain.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@Table(name = "order_counters", schema = "ordering")
@NoArgsConstructor
@AllArgsConstructor
public class OrderCounter {
    @Id
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "current_year", nullable = false)
    private int currentYear;

    @Column(name = "last_number", nullable = false)
    private int lastNumber;

}
