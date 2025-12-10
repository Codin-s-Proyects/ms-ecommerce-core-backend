package codin.msbackendcore.search.infrastructure.persistence.jpa;

import codin.msbackendcore.search.domain.model.entities.ProductEmbedding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductEmbeddingRepository extends JpaRepository<ProductEmbedding, UUID> {

    @Query(value = """
            SELECT
                emb.*
            FROM search.product_embeddings emb
            WHERE emb.tenant_id = :tenantId
            AND (emb.vector <-> CAST(:queryEmbedding AS vector)) < :distanceThreshold
            ORDER BY emb.vector <-> CAST(:queryEmbedding AS vector)
            LIMIT :limit
            """, nativeQuery = true)
    List<ProductEmbedding> findNearestEmbeddingsByTenant(
            @Param("tenantId") UUID tenantId,
            @Param("queryEmbedding") float[] queryEmbedding,
            @Param("limit") int limit,
            @Param("distanceThreshold") Double distanceThreshold
    );

    @Query(value = """
            SELECT
                emb.*
            FROM search.product_embeddings emb
            WHERE (emb.vector <-> CAST(:queryEmbedding AS vector)) < :distanceThreshold
            ORDER BY emb.vector <-> CAST(:queryEmbedding AS vector)
            LIMIT :limit
            """, nativeQuery = true)
    List<ProductEmbedding> findNearestEmbeddings(
            @Param("queryEmbedding") float[] queryEmbedding,
            @Param("limit") int limit,
            @Param("distanceThreshold") Double distanceThreshold
    );


}
