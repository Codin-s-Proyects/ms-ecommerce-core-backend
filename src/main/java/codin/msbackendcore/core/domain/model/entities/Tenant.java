package codin.msbackendcore.core.domain.model.entities;


import codin.msbackendcore.core.domain.model.valueobjects.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tenants", schema = "core")
public class Tenant {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "slug", columnDefinition = "CITEXT", nullable = false)
    private String slug;

    @Column(name = "name", columnDefinition = "TEXT", nullable = false)
    private String name;

    @Column(name = "complaint_book_url", columnDefinition = "TEXT")
    private String complaintBookUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @Embedded
    private LegalInfo legal;

    @Column(name = "status", columnDefinition = "TEXT")
    private String status = "active";

    @Embedded
    private ContactInfo contact;

    @Embedded
    private SupportInfo support;

    @Embedded
    private SocialInfo social;

    @Column(name = "currency_code", columnDefinition = "TEXT")
    private String currencyCode = "PEN";

    @Column(name = "locale", columnDefinition = "TEXT")
    private String locale = "es";

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TenantAddress> addresses = new ArrayList<>();

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public void addAddress(TenantAddress address) {
        address.setTenant(this);
        this.addresses.add(address);
    }

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }
}
