-- Permitir NULL en ai_context
ALTER TABLE core.media_assets
    ALTER COLUMN ai_context DROP NOT NULL;

-- Permitir NULL en context
ALTER TABLE core.media_assets
    ALTER COLUMN context DROP NOT NULL;

-- Permitir NULL en asset_meta
ALTER TABLE core.media_assets
    ALTER COLUMN asset_meta DROP NOT NULL;
