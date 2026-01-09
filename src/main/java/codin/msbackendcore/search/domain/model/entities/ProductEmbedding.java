package codin.msbackendcore.search.domain.model.entities;

import codin.msbackendcore.search.domain.model.valueobjects.ProductEmbeddingSourceType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Builder
@Entity
@Table(name = "product_embeddings", schema = "search")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEmbedding {
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "tenant_id")
    private UUID tenantId;

    @NotNull
    @Column(name = "product_variant_id")
    private UUID productVariantId;

    @Column(name = "vector", columnDefinition = "vector(1536)", nullable = false)
    @JsonIgnore
    private String vector;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", nullable = false)
    private ProductEmbeddingSourceType sourceType;

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

