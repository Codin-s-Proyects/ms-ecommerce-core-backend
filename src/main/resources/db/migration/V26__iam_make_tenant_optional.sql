-- =========================================
-- IAM: tenant_id opcional para users y sessions
-- =========================================

-- 1. Users: permitir usuarios sin tenant (clientes)
ALTER TABLE iam.users
    ALTER COLUMN tenant_id DROP NOT NULL;

-- 2. Sessions: permitir sesiones sin tenant
ALTER TABLE iam.sessions
    ALTER COLUMN tenant_id DROP NOT NULL;
