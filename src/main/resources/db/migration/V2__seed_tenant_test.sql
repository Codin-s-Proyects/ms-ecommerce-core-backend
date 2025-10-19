-- =============================================
-- Seed inicial: tenant de prueba
-- =============================================

INSERT INTO core.tenants (id, slug, name, plan, created_at)
VALUES (
           'bf89c25a-3ed4-4a00-96bd-59cde2ced8c0',
           'tenat-de-prueba',
           'Tenant de Prueba',
           'BASIC',
           now()
       )
ON CONFLICT (slug) DO NOTHING;

INSERT INTO core.tenant_settings (id, tenant_id, image_prompt, composer_prompt, created_at, updated_at)
VALUES (
           '54272f57-3ab2-4aa1-bd9a-3efe4dd0adc0',
           'bf89c25a-3ed4-4a00-96bd-59cde2ced8c0',
           'ESTO EST UN TEXTO MUY GRANDE PARA EL IMAGE PROMPT',
           'ESTO EST UN TEXTO MUY GRANDE PARA EL IMAGE COMPOSER',
           now(),
           now()
);