package codin.msbackendcore.iam.domain.model.entities;

import codin.msbackendcore.ordering.domain.model.valueobjects.DocumentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_profiles", schema = "iam",
        uniqueConstraints = @UniqueConstraint(columnNames = {"document_number"}))
public class UserProfile {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @Column(name = "tenant_id")
    private UUID tenantId;

    @NotBlank
    @Column(name = "first_name", columnDefinition = "text", nullable = false)
    private String firstName;

    @NotBlank
    @Column(name = "last_name", columnDefinition = "text", nullable = false)
    private String lastName;

    @NotBlank
    @Column(name = "email", columnDefinition = "text", nullable = false)
    private String email;

    @NotBlank
    @Column(name = "phone", columnDefinition = "text", nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", columnDefinition = "text")
    private DocumentType documentType;

    @Column(name = "document_number", columnDefinition = "text")
    private String documentNumber;

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
