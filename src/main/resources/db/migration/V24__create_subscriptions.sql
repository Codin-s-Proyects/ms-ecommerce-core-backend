-- =========================================
-- SUBSCRIPTIONS TABLE
-- =========================================
CREATE TABLE payment.subscriptions
(
    id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    tenant_id uuid NOT NULL REFERENCES core.tenants(id),
    plan_id uuid NOT NULL,
    status text NOT NULL,
    started_at timestamptz NOT NULL,
    next_billing_at timestamptz NOT NULL,
    created_at timestamptz NOT NULL DEFAULT now()
);

-- =========================================
-- INDEXES
-- =========================================

CREATE INDEX idx_subscriptions_status
    ON payment.subscriptions (status);
