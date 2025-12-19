-- =========================================
-- SALE COMMISSIONS TABLE
-- =========================================
CREATE TABLE payment.sale_commissions
(
    id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    tenant_id uuid NOT NULL REFERENCES core.tenants(id),
    order_id uuid NOT NULL REFERENCES ordering.orders(id),
    payment_id uuid NOT NULL REFERENCES payment.payments(id),
    user_id uuid NOT NULL,
    gross_amount      numeric(12, 2) NOT NULL,
    commission_amount numeric(12, 2) NOT NULL,
    merchant_amount   numeric(12, 2) NOT NULL,
    commission_rate   numeric(5, 2)  NOT NULL,
    plan_id uuid NOT NULL REFERENCES core.plans(id),
    created_at timestamptz NOT NULL DEFAULT now(),

    CONSTRAINT chk_commission_total
        CHECK (gross_amount = commission_amount + merchant_amount)
);

-- =========================================
-- INDEXES
-- =========================================
CREATE INDEX idx_commission_payment
    ON payment.sale_commissions (payment_id);

CREATE INDEX idx_commission_order
    ON payment.sale_commissions (order_id);
