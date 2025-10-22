package codin.msbackendcore.search.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "product_embeddings", schema = "search")
@Getter
@Setter
public class ProductEmbedding {
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    private UUID tenantId;

    @NotNull
    private UUID productVariantId;

    @Column(name = "vector", columnDefinition = "vector(1536)", nullable = false)
    private float[] vector;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> metadata = new HashMap<>();

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }
}

