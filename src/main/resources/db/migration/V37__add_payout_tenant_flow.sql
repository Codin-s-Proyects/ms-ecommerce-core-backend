-- =========================================
-- TENANT BANK ACCOUNTS
-- =========================================
CREATE TABLE core.tenant_bank_accounts
(
    id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
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

    CONSTRAINT uq_tenant_default_account
        UNIQUE (tenant_id)
);


-- =========================================
-- TENANT PAYOUTS
-- =========================================
CREATE TABLE payment.tenant_payouts
(
    id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    tenant_id uuid NOT NULL REFERENCES core.tenants(id),
    order_id uuid NOT NULL REFERENCES ordering.orders(id),
    sale_commission_id uuid NOT NULL REFERENCES payment.sale_commissions(id),
    payout_amount    numeric(18, 6) NOT NULL CHECK (payout_amount > 0),
    payout_method    TEXT           NOT NULL DEFAULT 'BANK_TRANSFER',
    payout_reference TEXT,
    payout_notes     TEXT,
    status           TEXT           NOT NULL,
    executed_by uuid,
    executed_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_tenant_payouts_tenant
    ON payment.tenant_payouts (tenant_id);

CREATE INDEX idx_tenant_payouts_status
    ON payment.tenant_payouts (status);

CREATE INDEX idx_tenant_payouts_order
    ON payment.tenant_payouts (order_id);
