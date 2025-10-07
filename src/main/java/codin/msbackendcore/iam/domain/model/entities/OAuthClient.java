package codin.msbackendcore.iam.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "oauth_clients", schema = "iam",
        uniqueConstraints = @UniqueConstraint(columnNames = {"tenant_id", "client_id"}))
public class OAuthClient {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    @NotNull
    private UUID tenantId;

    @Column(name = "client_id", nullable = false)
    @NotBlank
    private String clientId;

    private String clientSecretHash;
    private String name;

    @ElementCollection
    @CollectionTable(name = "oauth_redirect_uris", schema = "iam")
    private List<String> redirectUris;

    @ElementCollection
    @CollectionTable(name = "oauth_grant_types", schema = "iam")
    private List<String> grantTypes;

    @ElementCollection
    @CollectionTable(name = "oauth_scopes", schema = "iam")
    private List<String> scopes;

    @Column(name = "is_confidential", nullable = false)
    private boolean confidential = true;

    private Instant createdAt;

    @PrePersist
    void prePersist() {
        this.createdAt = Instant.now();
    }
}

