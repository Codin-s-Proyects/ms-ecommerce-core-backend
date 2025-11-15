-- ============================================
-- 1) Nuevas columnas en core.tenants
-- ============================================

ALTER TABLE core.tenants
    ADD COLUMN IF NOT EXISTS legal_name    TEXT,
    ADD COLUMN IF NOT EXISTS tax_id        TEXT,
    ADD COLUMN IF NOT EXISTS status        TEXT DEFAULT 'active',
    ADD COLUMN IF NOT EXISTS contact_name  TEXT,
    ADD COLUMN IF NOT EXISTS contact_email TEXT,
    ADD COLUMN IF NOT EXISTS contact_phone TEXT,
    ADD COLUMN IF NOT EXISTS support_email TEXT,
    ADD COLUMN IF NOT EXISTS support_phone TEXT,
    ADD COLUMN IF NOT EXISTS whatsapp      TEXT,
    ADD COLUMN IF NOT EXISTS facebook      TEXT,
    ADD COLUMN IF NOT EXISTS instagram     TEXT,
    ADD COLUMN IF NOT EXISTS twitter       TEXT,
    ADD COLUMN IF NOT EXISTS currency_code TEXT DEFAULT 'PEN',
    ADD COLUMN IF NOT EXISTS locale        TEXT DEFAULT 'es';

-- ============================================
-- 2) Tabla de direcciones del tenant
-- ============================================

CREATE TABLE IF NOT EXISTS core.tenant_addresses
(
    id         UUID PRIMARY KEY     DEFAULT gen_random_uuid(),
    tenant_id  UUID        NOT NULL REFERENCES core.tenants (id) ON DELETE CASCADE,
    line1      TEXT        NOT NULL,
    city       TEXT,
    country    TEXT        NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_tenant_addresses_tenant
    ON core.tenant_addresses (tenant_id);
