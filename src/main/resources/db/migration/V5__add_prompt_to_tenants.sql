-- =====================================================
-- Add column prompt_settings to core.tenants table
-- =====================================================

ALTER TABLE core.tenants
    ADD COLUMN prompt_settings jsonb NOT NULL DEFAULT '{}'::jsonb;