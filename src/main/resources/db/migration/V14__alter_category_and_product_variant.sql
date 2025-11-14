-- ============================================
-- 1) Eliminar columna description de category
-- ============================================
ALTER TABLE catalog.categories
    DROP COLUMN IF EXISTS description;

-- ============================================
-- 2) Agregar cantidad de producto a product_variant
-- ============================================
ALTER TABLE catalog.product_variants
    ADD COLUMN IF NOT EXISTS product_quantity INTEGER NOT NULL DEFAULT 0;
