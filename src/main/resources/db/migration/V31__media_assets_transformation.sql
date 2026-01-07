ALTER TABLE core.media_assets
    ADD COLUMN usage text NOT NULL DEFAULT 'MARKETPLACE';

-- Solo una imagen principal por entidad + usage
CREATE UNIQUE INDEX uq_media_main_per_usage
    ON core.media_assets (tenant_id, entity_type, entity_id, usage) WHERE is_main = true;


-- Nueva columna
ALTER TABLE core.media_assets
    ADD COLUMN asset_meta jsonb NOT NULL DEFAULT '{}'::jsonb;

-- Migrar datos existentes
UPDATE core.media_assets
SET asset_meta = jsonb_build_object(
        'format', format,
        'width', width,
        'height', height,
        'bytes', bytes,
        'altText', alt_text
                 );

-- Eliminar columnas antiguas
ALTER TABLE core.media_assets
    DROP COLUMN format,
    DROP COLUMN width,
    DROP COLUMN height,
    DROP COLUMN bytes,
    DROP COLUMN alt_text;



ALTER TABLE core.media_assets
    ADD COLUMN ai_context text NOT NULL DEFAULT 'NONE';
