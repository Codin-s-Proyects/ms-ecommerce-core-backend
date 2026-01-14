package codin.msbackendcore.search.infrastructure.persistence.jpa;

import codin.msbackendcore.search.domain.model.entities.ProductEmbedding;
import codin.msbackendcore.search.infrastructure.persistence.dto.ProductEmbeddingView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductEmbeddingRepository extends JpaRepository<ProductEmbedding, UUID> {


    @Query(value = """
                SELECT emb.tenant_id AS tenantId,
                       emb.product_variant_id AS productVariantId
                FROM search.product_embeddings emb
                WHERE (:tenantId IS NULL OR emb.tenant_id = :tenantId)
                  AND emb.source_type = :sourceType
                ORDER BY emb.vector <-> CAST(:queryEmbedding AS vector)
                LIMIT :limit
            """, nativeQuery = true)
    List<ProductEmbeddingView> search(
            @Param("tenantId") UUID tenantId,
            @Param("queryEmbedding") float[] queryEmbedding,
            @Param("sourceType") String sourceType,
            @Param("limit") int limit,
            @Param("distanceThreshold") Double distanceThreshold
    );


}
