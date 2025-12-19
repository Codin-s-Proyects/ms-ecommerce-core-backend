-- =========================================
-- EXTEND PAYMENT TABLE
-- =========================================
ALTER TABLE payment.payments
    ADD COLUMN payment_type text NOT NULL DEFAULT 'SALE';

-- =========================================
-- INDEXES
-- =========================================
CREATE INDEX idx_payments_type
    ON payment.payments (payment_type);

-- =========================================
-- COMMENTS
-- =========================================
COMMENT ON COLUMN payment.payments.payment_type IS 'SALE | SUBSCRIPTION';
