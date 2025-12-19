ALTER TABLE payment.payments
    ADD COLUMN user_id uuid NOT NULL REFERENCES iam.users (id) ON DELETE CASCADE;

CREATE INDEX idx_payments_user_id
    ON payment.payments (tenant_id, user_id);