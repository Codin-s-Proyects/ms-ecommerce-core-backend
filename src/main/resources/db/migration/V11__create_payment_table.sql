-- =========================================
-- SCHEMA
-- =========================================
CREATE SCHEMA IF NOT EXISTS payment;

-- =========================================
-- PAYMENT TABLE
-- =========================================
CREATE TABLE payment.payments
(
    id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    tenant_id uuid NOT NULL REFERENCES core.tenants (id) ON DELETE CASCADE,
    order_id uuid NOT NULL REFERENCES ordering.orders (id) ON DELETE CASCADE,
    payment_method text,
    status         text           NOT NULL,
    transaction_id text           NOT NULL UNIQUE,
    amount         numeric(12, 2) NOT NULL CHECK (amount > 0),
    created_at timestamptz NOT NULL DEFAULT now(),
    confirmed_at timestamptz,

    CONSTRAINT uq_payment_transaction UNIQUE (tenant_id, transaction_id)
);

-- =========================================
-- INDEXES
-- =========================================
CREATE INDEX idx_payment_tenant ON payment.payments (tenant_id);
CREATE INDEX idx_payment_order ON payment.payments (order_id);
CREATE INDEX idx_payment_status ON payment.payments (status);

-- =========================================
-- TRIGGERS
-- =========================================
CREATE
OR
REPLACE FUNCTION set_payment_confirmed_at()
RETURNS TRIGGER AS
$$
BEGIN
    -- Si el estado pasa a SUCCESS y no tenía confirmed_at, la setea automáticamente
    IF NEW.status = 'SUCCESS' AND NEW.confirmed_at IS NULL THEN
        NEW.confirmed_at = now();
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_payment_confirmed
    BEFORE UPDATE
    ON payment.payments
    FOR EACH ROW
    WHEN
    (NEW.status = 'SUCCESS' AND OLD.status <> 'SUCCESS')
EXECUTE FUNCTION set_payment_confirmed_at();

-- =========================================
-- RLS (Row Level Security)
-- =========================================
ALTER TABLE payment.payments ENABLE ROW LEVEL SECURITY;

CREATE
POLICY p_payments_tenant ON payment.payments
    USING (tenant_id = current_setting('app.tenant_id')::uuid);

-- =========================================
-- COMMENTS (documentación interna)
-- =========================================
COMMENT ON TABLE payment.payments IS 'Registra los pagos asociados a órdenes, incluyendo transacciones con Izipay u otros métodos.';
COMMENT ON COLUMN payment.payments.payment_method IS 'Método de pago utilizado (CREDIT_CARD, DEBIT_CARD, CASH, TRANSFER, IZIPAY, etc.)';
COMMENT ON COLUMN payment.payments.status IS 'Estado del pago (PENDING, SUCCESS, FAILED, CANCELLED, REFUNDED)';
COMMENT ON COLUMN payment.payments.transaction_id IS 'Identificador único de la transacción, generado internamente y enviado al proveedor de pagos.';
COMMENT ON COLUMN payment.payments.amount IS 'Monto total del pago, en la moneda configurada para el tenant.';
COMMENT ON COLUMN payment.payments.confirmed_at IS 'Fecha y hora en que el pago fue confirmado exitosamente.';
