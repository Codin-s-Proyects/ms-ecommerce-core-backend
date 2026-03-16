-- =========================================
-- EXTEND ORDER SHIPPING ADDRESSES
-- =========================================

ALTER TABLE ordering.order_shipping_addresses
    ADD COLUMN shipping_provider text,
    ADD COLUMN shipping_service text,
    ADD COLUMN shipping_cost numeric(12,2),
    ADD COLUMN provider_metadata jsonb,
    ADD COLUMN shipped_at timestamptz;

-- =========================================
-- USER SHIPPING ADDRESSES
-- =========================================

CREATE TABLE ordering.user_shipping_addresses
(
    id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    tenant_id uuid NOT NULL
        REFERENCES core.tenants(id) ON DELETE CASCADE,
    user_id uuid NOT NULL
        REFERENCES iam.users(id) ON DELETE CASCADE,
    label text,
    department   text NOT NULL,
    province     text NOT NULL,
    district     text NOT NULL,
    address_line text NOT NULL,
    reference    text,
    latitude  numeric(10,7),
    longitude numeric(10,7),
    preferred_shipping_provider text,
    preferred_shipping_service  text,
    is_default boolean DEFAULT false,
    created_at timestamptz NOT NULL DEFAULT now(),
    updated_at timestamptz
);


-- =========================================
-- INDEXES
-- =========================================

CREATE INDEX idx_user_shipping_addresses_user
    ON ordering.user_shipping_addresses (tenant_id, user_id);

CREATE INDEX idx_user_shipping_default
    ON ordering.user_shipping_addresses (tenant_id, user_id, is_default);