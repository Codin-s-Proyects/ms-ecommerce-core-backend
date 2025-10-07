package codin.msbackendcore.iam.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity
@Table(name = "credentials", schema = "iam",
        uniqueConstraints = @UniqueConstraint(columnNames = {"type", "identifier"}))
public class Credential {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @Column(nullable = false)
    @NotBlank
    private String type;

    @Column(nullable = false)
    @NotBlank
    private String identifier;

    private String secretHash;

    @Column(name = "is_primary", nullable = false)
    private boolean primary;

    // getters y setters
}
