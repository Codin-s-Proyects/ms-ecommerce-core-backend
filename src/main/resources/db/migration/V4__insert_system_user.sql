-- =====================================================
-- Create a system user for internal automated actions
-- =====================================================

DO $$ DECLARE
    tenant RECORD;
BEGIN
    -- Loop all tenants to create one system user per tenant
    FOR tenant IN
SELECT id
FROM core.tenants LOOP
        -- Insert system user if not already exists
        IF NOT EXISTS (
            SELECT 1 FROM iam.users
            WHERE tenant_id = tenant.id
              AND user_type = 'system'
        ) THEN
INSERT INTO iam.users (tenant_id,
                       user_type,
                       is_active,
                       mfa_enabled,
                       meta,
                       created_at)
VALUES (tenant.id,
        'system',
        TRUE,
        FALSE,
        '{"description": "System automation user"}'::jsonb,
        now());
END IF;
END LOOP;
END $$;
