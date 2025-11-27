--------------------------------------------------------
-- 1) Eliminar NOT NULL en tenant_id para categories
--------------------------------------------------------
ALTER TABLE catalog.categories
    ALTER COLUMN tenant_id DROP NOT NULL;

--------------------------------------------------------
-- 2) Actualizar la constraint UNIQUE existente
--    Antes: UNIQUE (tenant_id, slug) con tenant_id NOT NULL
--    Ahora debe permitir tenant_id NULL (global) y seguir
--    garantizando unicidad por tenant
--------------------------------------------------------
ALTER TABLE catalog.categories
    DROP CONSTRAINT IF EXISTS categories_tenant_id_slug_key;

ALTER TABLE catalog.categories
    ADD CONSTRAINT uq_categories_tenant_slug
        UNIQUE (tenant_id, slug);

--------------------------------------------------------
-- 3) Ajustar políticas RLS:
--    Las categorías globales (tenant_id IS NULL) deben ser
--    visibles para todos los tenants.
--------------------------------------------------------

-- Deshabilitar política anterior
DROP POLICY IF EXISTS p_categories_tenant ON catalog.categories;

-- Crear política nueva
CREATE POLICY p_categories_tenant ON catalog.categories
    USING (
    tenant_id IS NULL
        OR tenant_id = current_setting('app.tenant_id')::uuid
    );

--------------------------------------------------------
-- 4) Comentario opcional para documentación (recomendado)
--------------------------------------------------------
COMMENT ON COLUMN catalog.categories.tenant_id IS
    'NULL = categoría global. UUID = categoría propia del tenant.';
