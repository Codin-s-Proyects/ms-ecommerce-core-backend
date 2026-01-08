-- =========================================
-- 2. Ajustar tabla product_embeddings
-- =========================================
ALTER TABLE search.product_embeddings
    ADD COLUMN source_type TEXT NOT NULL DEFAULT 'COMPOSITE';

-- =========================================
-- 3. Constraint de unicidad
-- Un embedding por variante y tipo
-- =========================================
ALTER TABLE search.product_embeddings
    ADD CONSTRAINT uq_product_variant_embedding
        UNIQUE (tenant_id, product_variant_id, source_type);

-- =========================================
-- 4. Índices pgvector por tipo
-- =========================================

-- COMPOSITE (principal, chatbot, búsqueda general)
CREATE INDEX idx_embeddings_composite
    ON search.product_embeddings
    USING ivfflat (vector)
    WHERE source_type = 'COMPOSITE';

-- IMAGE_ONLY (búsqueda por imagen)
CREATE INDEX idx_embeddings_image
    ON search.product_embeddings
    USING ivfflat (vector)
    WHERE source_type = 'IMAGE_ONLY';

-- TEXT_ONLY (opcional / futuro)
CREATE INDEX idx_embeddings_text
    ON search.product_embeddings
    USING ivfflat (vector)
    WHERE source_type = 'TEXT_ONLY';

-- =========================================
-- 5. Seguridad y limpieza
-- =========================================
ALTER TABLE search.product_embeddings
    ALTER COLUMN metadata SET DEFAULT '{}'::jsonb;
