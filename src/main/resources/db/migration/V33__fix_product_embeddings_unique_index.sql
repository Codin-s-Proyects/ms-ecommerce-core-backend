-- =====================================================
-- Remove obsolete unique constraint that blocks
-- multiple embeddings per source_type
-- =====================================================

DROP INDEX IF EXISTS search.uq_product_embeddings_tenant_variant;

-- =====================================================
-- Ensure correct unique constraint exists
-- =====================================================

CREATE UNIQUE INDEX IF NOT EXISTS uq_product_variant_embedding
    ON search.product_embeddings (tenant_id, product_variant_id, source_type);
