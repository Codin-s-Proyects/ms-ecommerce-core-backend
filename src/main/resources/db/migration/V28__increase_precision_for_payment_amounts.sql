-- =========================================
-- Increase precision for monetary values
-- =========================================

-- Payments
ALTER TABLE payment.payments
ALTER COLUMN amount TYPE numeric(18, 6);

-- Sale commissions
ALTER TABLE payment.sale_commissions
DROP CONSTRAINT chk_commission_total;

ALTER TABLE payment.sale_commissions
ALTER COLUMN gross_amount TYPE numeric(18, 6),
    ALTER COLUMN commission_amount TYPE numeric(18, 6),
    ALTER COLUMN merchant_amount TYPE numeric(18, 6),
    ALTER COLUMN commission_rate TYPE numeric(7, 6);

ALTER TABLE payment.sale_commissions
    ADD CONSTRAINT chk_commission_total
        CHECK (gross_amount = commission_amount + merchant_amount);
