-- =============================================
-- Seed inicial: tenant de prueba
-- =============================================

INSERT INTO core.tenants (id, slug, name, plan, settings, created_at)
VALUES (
           uuid_generate_v4(),
           'tenant_test',
           'Tenant de Prueba',
           'free',
           '{}'::jsonb,
           now()
       )
ON CONFLICT (slug) DO NOTHING;
