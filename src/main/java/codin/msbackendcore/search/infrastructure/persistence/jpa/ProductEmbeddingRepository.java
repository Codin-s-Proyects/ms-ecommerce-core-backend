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

    @Query(
            value = """
                    SELECT
                        jsonb_build_object(
                            'product', jsonb_build_object(
                                'id', p.id,
                                'tenantId', p.tenant_id,
                                'name', p.name,
                                'slug', p.slug,
                                'description', p.description,
                                'hasVariants', p.has_variants,
                                'mediaAssets', COALESCE((
                                    SELECT jsonb_agg(
                                        jsonb_build_object(
                                            'id', pm.id,
                                            'entityType', pm.entity_type,
                                            'entityId', pm.entity_id,
                                            'url', pm.url,
                                            'publicId', pm.public_id,
                                            'isMain', pm.is_main,
                                            'sortOrder', pm.sort_order,
                                            'assetMeta', pm.asset_meta,
                                            'context', pm.context,
                                            'usage', pm.usage,
                                            'aiContext', pm.ai_context
                                        )
                                        ORDER BY pm.sort_order
                                    )
                                    FROM core.media_assets pm
                                    WHERE pm.entity_id = p.id
                                      AND pm.entity_type = 'PRODUCT'
                                ), '[]'::jsonb)
                            ),
                            'productVariant', jsonb_build_object(
                                'id', pv.id,
                                'tenantId', pv.tenant_id,
                                'sku', pv.sku,
                                'name', pv.name,
                                'attributes', pv.attributes,
                                'productQuantity', pv.product_quantity,
                                'mediaAssets', COALESCE((
                                    SELECT jsonb_agg(
                                        jsonb_build_object(
                                            'id', vm.id,
                                            'entityType', vm.entity_type,
                                            'entityId', vm.entity_id,
                                            'url', vm.url,
                                            'publicId', vm.public_id,
                                            'isMain', vm.is_main,
                                            'sortOrder', vm.sort_order,
                                            'assetMeta', vm.asset_meta,
                                            'context', vm.context,
                                            'usage', vm.usage,
                                            'aiContext', vm.ai_context
                                        )
                                        ORDER BY vm.sort_order
                                    )
                                    FROM core.media_assets vm
                                    WHERE vm.entity_id = pv.id
                                      AND vm.entity_type = 'PRODUCT_VARIANT'
                                ), '[]'::jsonb)
                            ),
                            'productPrices', COALESCE((
                                SELECT jsonb_agg(
                                    jsonb_build_object(
                                        'id', pr.id,
                                        'tenantId', pr.tenant_id,
                                        'productVariantId', pr.product_variant_id,
                                        'priceList', COALESCE((
                                            SELECT jsonb_build_object(
                                                'id', pl.id,
                                                'tenantId', pl.tenant_id,
                                                'code', pl.code,
                                                'name', pl.name,
                                                'description', pl.description,
                                                'currencyCode', pl.currency_code,
                                                'validFrom', pl.valid_from,
                                                'validTo', pl.valid_to,
                                                'status', pl.status
                                            )
                                            FROM pricing.price_lists pl
                                            WHERE pl.id = pr.price_list_id
                                        ), '{}'::jsonb),
                                        'discountPercent', pr.discount_percent,
                                        'finalPrice', pr.final_price,
                                        'basePrice', pr.base_price,
                                        'minQuantity', pr.min_quantity,
                                        'validFrom', pr.valid_from,
                                        'validTo', pr.valid_to
                                    )
                                )
                                FROM pricing.product_prices pr
                                WHERE pr.product_variant_id = pv.id
                                  AND (pr.valid_to IS NULL OR pr.valid_to > now())
                            ), '[]'::jsonb)
                        ) AS result
                    FROM catalog.product_variants pv
                    JOIN catalog.products p ON p.id = pv.product_id
                    WHERE (:tenantId IS NULL OR pv.tenant_id = :tenantId)
                      AND pv.id = ANY(:variantIds)
                      AND p.status = 'ACTIVE'
                      AND pv.status = 'ACTIVE'
                    """,
            nativeQuery = true
    )
    List<String> findSemanticDetails(
            @Param("tenantId") UUID tenantId,
            @Param("variantIds") UUID[] variantIds
    );


}
