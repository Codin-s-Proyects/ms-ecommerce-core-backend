CREATE SCHEMA IF NOT EXISTS ordering;

-- =========================================
-- ORDERS (cabecera)
-- =========================================
CREATE TABLE ordering.orders
(
    id             uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    tenant_id      uuid NOT NULL REFERENCES core.tenants (id) ON DELETE CASCADE,
    user_id        uuid NOT NULL REFERENCES iam.users (id) ON DELETE CASCADE,
    order_number   text UNIQUE NOT NULL, -- Ej: ORD-2025-00001
    status         text NOT NULL, -- pending, paid, shipped, canceled
    currency_code  text NOT NULL DEFAULT 'PEN',
    subtotal       numeric(12,2) NOT NULL,
    discount_total numeric(12,2) DEFAULT 0,
    total          numeric(12,2) NOT NULL,
    notes          text,
    created_at     timestamptz NOT NULL DEFAULT now(),
    updated_at     timestamptz NOT NULL DEFAULT now()
);

-- =========================================
-- ORDER ITEMS (detalles)
-- =========================================
CREATE TABLE ordering.order_items
(
    id                 uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    tenant_id          uuid NOT NULL REFERENCES core.tenants (id) ON DELETE CASCADE,
    order_id           uuid NOT NULL REFERENCES ordering.orders (id) ON DELETE CASCADE,
    product_variant_id uuid NOT NULL REFERENCES catalog.product_variants (id),
    product_name       text NOT NULL,
    sku                text NOT NULL,
    attributes         jsonb NOT NULL DEFAULT '{}'::jsonb,
    quantity           int NOT NULL,
    unit_price         numeric(12,2) NOT NULL,
    discount_percent   numeric(5,2) DEFAULT 0.0,
    final_price        numeric(12,2) GENERATED ALWAYS AS (unit_price * (1 - discount_percent / 100.0)) STORED,
    total_price numeric(12,2) GENERATED ALWAYS AS (unit_price * (1 - discount_percent / 100.0) * quantity) STORED
);

-- =========================================
-- ORDER STATUS HISTORY (tracking)
-- =========================================
CREATE TABLE ordering.order_status_history
(
    id         uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    order_id   uuid NOT NULL REFERENCES ordering.orders (id) ON DELETE CASCADE,
    status     text NOT NULL,
    changed_at timestamptz NOT NULL DEFAULT now(),
    changed_by uuid REFERENCES iam.users (id)
);
