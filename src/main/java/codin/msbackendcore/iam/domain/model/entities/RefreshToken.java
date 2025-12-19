package codin.msbackendcore.iam.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @Column(name = "token_hash", nullable = false)
    @NotBlank
    private String tokenHash;

    @Transient
    private String plainToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    private Instant issuedAt;
    private Instant expiresAt;
    private boolean revoked = false;

    @PrePersist
    void prePersist() {
        this.issuedAt = Instant.now();
    }
}
