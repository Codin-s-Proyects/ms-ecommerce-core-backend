-- =========================================
-- SCHEMA
-- =========================================
CREATE SCHEMA IF NOT EXISTS pricing;

-- =========================================
-- PRICE LISTS
-- =========================================
CREATE TABLE pricing.price_lists
(
    id            uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    tenant_id     uuid    NOT NULL REFERENCES core.tenants (id) ON DELETE CASCADE,
    code          text    NOT NULL, -- Ej: "default", "mayorista", "minorista", "navidad_2025"
    name          text    NOT NULL,
    description   text,
    currency_code text    NOT NULL DEFAULT 'PEN',
    is_active     boolean NOT NULL DEFAULT true,
    valid_from    timestamptz      DEFAULT now(),
    valid_to      timestamptz,
    created_at    timestamptz      DEFAULT now(),
    updated_at    timestamptz      DEFAULT now(),
    UNIQUE (tenant_id, code)
);

-- =========================================
-- PRODUCT PRICES
-- =========================================
CREATE TABLE pricing.product_prices
(
    id                 uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    tenant_id          uuid           NOT NULL REFERENCES core.tenants (id) ON DELETE CASCADE,
    product_variant_id uuid           NOT NULL REFERENCES catalog.product_variants (id) ON DELETE CASCADE,
    price_list_id      uuid           NOT NULL REFERENCES pricing.price_lists (id) ON DELETE CASCADE,
    base_price         numeric(12, 2) NOT NULL,
    discount_percent   numeric(5, 2)    DEFAULT 0.0,
    final_price        numeric(12, 2) GENERATED ALWAYS AS (base_price * (1 - discount_percent / 100.0)) STORED,
    min_quantity       int              DEFAULT 1,
    valid_from         timestamptz      DEFAULT now(),
    valid_to           timestamptz,
    created_at         timestamptz      DEFAULT now(),
    updated_at         timestamptz      DEFAULT now(),
    UNIQUE (tenant_id, product_variant_id, price_list_id)
);

-- =========================================
-- DISCOUNTS (Opcional: reglas generales)
-- =========================================
CREATE TABLE pricing.discounts
(
    id          uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    tenant_id   uuid          NOT NULL REFERENCES core.tenants (id) ON DELETE CASCADE,
    name        text          NOT NULL,
    description text,
    percentage  numeric(5, 2) NOT NULL,
    starts_at   timestamptz      DEFAULT now(),
    ends_at     timestamptz,
    active      boolean          DEFAULT true,
    created_at  timestamptz      DEFAULT now()
);

-- =========================================
-- RELATION PRODUCT <-> DISCOUNT (Opcional)
-- =========================================
CREATE TABLE pricing.product_discounts
(
    id                 uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    tenant_id          uuid NOT NULL REFERENCES core.tenants (id) ON DELETE CASCADE,
    product_variant_id uuid NOT NULL REFERENCES catalog.product_variants (id) ON DELETE CASCADE,
    discount_id        uuid NOT NULL REFERENCES pricing.discounts (id) ON DELETE CASCADE,
    UNIQUE (tenant_id, product_variant_id, discount_id)
);

-- =========================================
-- TRIGGER PARA UPDATED_AT
-- =========================================
CREATE OR REPLACE FUNCTION set_updated_at()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at = now();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_price_lists_updated
    BEFORE UPDATE
    ON pricing.price_lists
    FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_product_prices_updated
    BEFORE UPDATE
    ON pricing.product_prices
    FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- =========================================
-- RLS
-- =========================================
ALTER TABLE pricing.price_lists
    ENABLE ROW LEVEL SECURITY;
CREATE POLICY p_price_lists_tenant ON pricing.price_lists
    USING (tenant_id = current_setting('app.tenant_id')::uuid);

ALTER TABLE pricing.product_prices
    ENABLE ROW LEVEL SECURITY;
CREATE POLICY p_product_prices_tenant ON pricing.product_prices
    USING (tenant_id = current_setting('app.tenant_id')::uuid);

ALTER TABLE pricing.discounts
    ENABLE ROW LEVEL SECURITY;
CREATE POLICY p_discounts_tenant ON pricing.discounts
    USING (tenant_id = current_setting('app.tenant_id')::uuid);

ALTER TABLE pricing.product_discounts
    ENABLE ROW LEVEL SECURITY;
CREATE POLICY p_product_discounts_tenant ON pricing.product_discounts
    USING (tenant_id = current_setting('app.tenant_id')::uuid);

-- =========================================
-- INDEXES
-- =========================================
CREATE INDEX idx_price_lists_tenant ON pricing.price_lists (tenant_id);
CREATE INDEX idx_product_prices_tenant ON pricing.product_prices (tenant_id);
CREATE INDEX idx_discounts_tenant ON pricing.discounts (tenant_id);
CREATE INDEX idx_product_discounts_tenant ON pricing.product_discounts (tenant_id);
