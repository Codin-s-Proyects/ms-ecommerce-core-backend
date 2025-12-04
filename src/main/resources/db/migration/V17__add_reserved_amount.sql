ALTER TABLE catalog.product_variants
    ADD COLUMN IF NOT EXISTS reserved_quantity INTEGER NOT NULL DEFAULT 0 ;

-- Opcional: índice para mejorar búsqueda por stock
CREATE INDEX IF NOT EXISTS idx_product_variant_stock
    ON catalog.product_variants (product_quantity, reserved_quantity);