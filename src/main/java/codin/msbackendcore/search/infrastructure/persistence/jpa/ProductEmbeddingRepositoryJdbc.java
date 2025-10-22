package codin.msbackendcore.search.infrastructure.persistence.jpa;

import codin.msbackendcore.search.domain.model.entities.ProductEmbedding;
import codin.msbackendcore.shared.domain.exceptions.ServerErrorException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Repository
public class ProductEmbeddingRepositoryJdbc {

    private final NamedParameterJdbcTemplate jdbc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ProductEmbeddingRepositoryJdbc(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void upsertEmbedding(UUID tenantId, UUID productVariantId, float[] vector, Map<String, Object> metadata) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < vector.length; i++) {
            if (i > 0) sb.append(',');
            sb.append(vector[i]);
        }
        sb.append(']');

        String sql = """
                INSERT INTO search.product_embeddings (id, tenant_id, product_variant_id, vector, metadata, created_at)
                VALUES (:id, :tenantId, :variantId, :vector::vector, :metadata::jsonb, :createdAt)
                ON CONFLICT (tenant_id, product_variant_id)
                DO UPDATE SET vector = EXCLUDED.vector, metadata = EXCLUDED.metadata, created_at = EXCLUDED.created_at;
                """;

        Map<String, Object> params = new HashMap<>();
        params.put("id", UUID.randomUUID());
        params.put("tenantId", tenantId);
        params.put("variantId", productVariantId);
        params.put("vector", sb.toString());
        try {
            params.put("metadata", objectMapper.writeValueAsString(metadata));
        } catch (Exception e) {
            throw new ServerErrorException("error.server_error", new String[]{});
        }
        params.put("createdAt", Timestamp.from(Instant.now()));

        jdbc.update(sql, params);
    }
}