package codin.msbackendcore.core.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tenant_addresses", schema = "core")
@Getter
@Setter
public class TenantAddress {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @Column(name = "line1", nullable = false)
    private String line1;

    @Column(name = "city")
    private String city;

    @Column(name = "country", nullable = false)
    private String country;
}