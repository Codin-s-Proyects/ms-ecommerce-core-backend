package codin.msbackendcore.iam.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "refresh_tokens", schema = "iam",
        uniqueConstraints = @UniqueConstraint(columnNames = "token_hash"))
public class RefreshToken {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    @NotNull
    private UUID tenantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @Column(name = "token_hash", nullable = false)
    @NotBlank
    private String tokenHash;

    private Instant issuedAt;
    private Instant expiresAt;
    private boolean revoked = false;

    private String deviceInfo;

    @PrePersist
    void prePersist() {
        this.issuedAt = Instant.now();
    }
}
