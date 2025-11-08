-- =========================================
-- V35__create_catalog_products_categories_table.sql
-- =========================================

-- 1️⃣ Crear tabla intermedia para relación N:M entre productos y categorías
CREATE TABLE catalog.product_categories
(
    id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    tenant_id uuid NOT NULL REFERENCES core.tenants (id) ON DELETE CASCADE,
    product_id uuid NOT NULL REFERENCES catalog.products (id) ON DELETE CASCADE,
    category_id uuid NOT NULL REFERENCES catalog.categories (id) ON DELETE CASCADE,
    created_at timestamptz NOT NULL DEFAULT now(),
    UNIQUE (tenant_id, product_id, category_id)
);

-- 2️⃣ Copiar datos existentes (migración de la relación 1:N original)
INSERT INTO catalog.product_categories (tenant_id, product_id, category_id)
SELECT tenant_id, id AS product_id, category_id
FROM catalog.products
WHERE category_id IS NOT NULL;

-- 3️⃣ Eliminar la columna antigua si ya no se usará (opcional pero recomendable)
ALTER TABLE catalog.products
    DROP COLUMN category_id;

-- 4️⃣ Crear índice para mejorar búsqueda
CREATE INDEX idx_product_categories_tenant
    ON catalog.product_categories (tenant_id, product_id, category_id);

-- 5️⃣ RLS
ALTER TABLE catalog.product_categories ENABLE ROW LEVEL SECURITY;

CREATE POLICY p_product_categories_tenant ON catalog.product_categories
    USING (tenant_id = current_setting('app.tenant_id')::uuid);
