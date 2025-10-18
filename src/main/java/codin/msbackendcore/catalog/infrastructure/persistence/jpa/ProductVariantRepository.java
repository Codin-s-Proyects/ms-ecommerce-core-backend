package codin.msbackendcore.catalog.infrastructure.persistence.jpa;

import codin.msbackendcore.catalog.domain.model.entities.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, UUID> {
    @Query(value = """
        SELECT pv.*
        FROM catalog.product_variants pv
        JOIN search.product_embeddings emb 
            ON emb.product_variant_id = pv.id
        WHERE pv.tenant_id = :tenantId
        ORDER BY emb.vector <-> CAST(:queryEmbedding AS vector)
        LIMIT :limit
        """, nativeQuery = true)
    List<ProductVariant> findMostSimilarProducts(
            @Param("tenantId") UUID tenantId,
            @Param("queryEmbedding") float[] queryEmbedding,
            @Param("limit") int limit
    );
}