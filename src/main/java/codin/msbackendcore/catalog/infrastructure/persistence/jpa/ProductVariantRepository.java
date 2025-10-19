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
        SELECT 
            pv.id AS variant_id,
            pv.name AS variant_name,
            pv.image_url AS variant_image_url,
            pv.sku AS sku,
            p.name AS product_name,
            p.description AS product_description,
            COALESCE(pp.final_price, 0) AS price
        FROM catalog.product_variants pv
        JOIN catalog.products p ON pv.product_id = p.id
        JOIN search.product_embeddings emb ON emb.product_variant_id = pv.id
        LEFT JOIN pricing.product_prices pp
            ON pp.product_variant_id = pv.id
            AND pp.tenant_id = pv.tenant_id
            AND pp.price_list_id = (
                SELECT id FROM pricing.price_lists 
                WHERE tenant_id = :tenantId AND code = 'minorista' LIMIT 1
            )
        WHERE pv.tenant_id = :tenantId
        ORDER BY emb.vector <-> CAST(:queryEmbedding AS vector)
        LIMIT :limit
        """, nativeQuery = true)
    List<Object[]> findEnrichedSemanticResults(
            @Param("tenantId") UUID tenantId,
            @Param("queryEmbedding") float[] queryEmbedding,
            @Param("limit") int limit
    );
}