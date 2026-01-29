-- =========================================
-- TENANT BANK ACCOUNTS
-- =========================================
CREATE TABLE IF NOT EXISTS core.tenant_bank_accounts
(
    id uuid PRIMARY KEY DEFAULT uuid_generate_v4 (),
    tenant_id uuid NOT NULL REFERENCES core.tenants(id) ON DELETE CASCADE,
    bank_name                TEXT    NOT NULL,
    account_type             TEXT    NOT NULL, -- SAVINGS | CHECKING
    account_holder           TEXT    NOT NULL,
    account_number_encrypted TEXT    NOT NULL,
    account_last4            TEXT    NOT NULL,
    currency_code            TEXT    NOT NULL DEFAULT 'PEN',
    status                   TEXT    NOT NULL DEFAULT 'PENDING',
    is_default               BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT uq_tenant_default_account UNIQUE (tenant_id)
);


-- =========================================
-- TENANT PAYOUTS
-- =========================================
CREATE TABLE IF NOT EXISTS payment.tenant_payouts
(
    id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    tenant_id uuid NOT NULL REFERENCES core.tenants(id),
    total_amount     numeric(18, 6) NOT NULL CHECK (total_amount > 0),
    payout_method    TEXT           NOT NULL DEFAULT 'BANK_TRANSFER',
    payout_reference TEXT,
    payout_notes     TEXT,
    status           TEXT           NOT NULL, -- PENDING | PROCESSING | PAID | FAILED
    executed_by uuid,
    executed_at timestamptz,
    created_at timestamptz NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_payout_tenant
    ON payment.tenant_payouts (tenant_id);

CREATE INDEX IF NOT EXISTS idx_payout_status
    ON payment.tenant_payouts (status);

CREATE TABLE IF NOT EXISTS payment.tenant_payout_items
(
    id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    tenant_payout_id uuid NOT NULL
        REFERENCES payment.tenant_payouts(id)
        ON DELETE CASCADE,
    sale_commission_id uuid NOT NULL
        REFERENCES payment.sale_commissions(id),
    order_id uuid NOT NULL
        REFERENCES ordering.orders(id),
    amount numeric(18, 6) NOT NULL CHECK (amount > 0),
    CONSTRAINT uq_payout_commission
        UNIQUE (tenant_payout_id, sale_commission_id)
);

CREATE INDEX IF NOT EXISTS idx_payout_items_payout
    ON payment.tenant_payout_items (tenant_payout_id);

ALTER TABLE payment.sale_commissions
    ADD COLUMN IF NOT EXISTS payout_status TEXT NOT NULL DEFAULT 'PENDING',
    ADD COLUMN IF NOT EXISTS paid_at timestamptz,
    ADD COLUMN IF NOT EXISTS currency_code TEXT NOT NULL;

ALTER TABLE payment.payments
    ADD COLUMN currency_code TEXT NOT NULL;

CREATE INDEX IF NOT EXISTS idx_sale_commission_tenant_status
    ON payment.sale_commissions (tenant_id, payout_status);


