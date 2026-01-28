package codin.msbackendcore.core.domain.model.entities;


import codin.msbackendcore.core.domain.model.valueobjects.AccountType;
import codin.msbackendcore.core.domain.model.valueobjects.BankName;
import codin.msbackendcore.core.domain.model.valueobjects.TenantBankAccountStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@Table(name = "tenant_bank_accounts", schema = "core")
@NoArgsConstructor
@AllArgsConstructor
public class TenantBankAccount {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @Enumerated(EnumType.STRING)
    @Column(name = "bank_name", columnDefinition = "TEXT", nullable = false)
    private BankName bankName;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", columnDefinition = "TEXT", nullable = false)
    private AccountType accountType;

    @Column(name = "account_holder", columnDefinition = "TEXT", nullable = false)
    private String accountHolder;

    @Column(name = "account_number_encrypted", columnDefinition = "TEXT", nullable = false)
    private String accountNumberEncrypted;

    @Column(name = "account_last4", columnDefinition = "TEXT", nullable = false)
    private String accountLast4;

    @Column(name = "currency_code", columnDefinition = "TEXT", nullable = false)
    private String currencyCode = "PEN";

    @Column(name = "status", columnDefinition = "TEXT", nullable = false)
    @Enumerated(EnumType.STRING)
    private TenantBankAccountStatus status;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
