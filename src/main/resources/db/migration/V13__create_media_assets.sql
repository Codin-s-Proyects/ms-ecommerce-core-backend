-- =========================================
-- MEDIA ASSETS (globales por tenant)
-- =========================================

CREATE TABLE core.media_assets
(
    id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    tenant_id uuid NOT NULL REFERENCES core.tenants(id) ON DELETE CASCADE,

    -- Polimorfismo: a qué entidad pertenece esta imagen
    entity_type text NOT NULL,         -- Ej: 'product', 'category', 'brand', 'variant', 'order'
    entity_id uuid NOT NULL,           -- ID de la entidad destino

    -- Datos del recurso
    url         text NOT NULL,         -- URL pública (Cloudinary o similar)
    public_id   text,                  -- ID para eliminar el recurso del proveedor
    format      text,                  -- jpg, png, webp, etc
    width       int,
    height      int,
    bytes       bigint,

    -- Información funcional
    is_main     boolean DEFAULT false, -- imagen principal
    sort_order  int     DEFAULT 0,     -- prioridad visual
    alt_text    text,
    context jsonb DEFAULT '{}'::jsonb, -- metadatos (por ejemplo, "angle": "front")

    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),

    CONSTRAINT uq_media_asset UNIQUE (tenant_id, entity_type, entity_id, url)
);

-- Índices útiles
CREATE INDEX idx_media_assets_tenant ON core.media_assets (tenant_id);
CREATE INDEX idx_media_assets_entity ON core.media_assets (entity_type, entity_id);
CREATE INDEX idx_media_assets_sort ON core.media_assets (entity_type, entity_id, sort_order);

ALTER TABLE catalog.product_variants
    DROP COLUMN IF EXISTS image_url;
ALTER TABLE catalog.brands
    DROP COLUMN IF EXISTS logo_url;

