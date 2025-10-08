-- =============================================
-- Seed inicial para la tabla iam.roles
-- =============================================

DO
$$
    DECLARE
        v_tenant_id uuid;
    BEGIN
        SELECT id INTO v_tenant_id FROM core.tenants LIMIT 1;
        IF v_tenant_id IS NULL THEN
            RAISE EXCEPTION 'No existe ningún tenant en core.tenants. Inserta uno antes de ejecutar este seed.';
        END IF;

        INSERT INTO iam.roles (id, tenant_id, code, name, description, created_at)
        VALUES (uuid_generate_v4(), v_tenant_id, 'ROLE_ADMIN', 'Administrador', 'Tiene acceso total al sistema', now()),
               (uuid_generate_v4(), v_tenant_id, 'ROLE_CUSTOMER', 'Cliente',
                'Usuario que consume los servicios del sistema', now()),
               (uuid_generate_v4(), v_tenant_id, 'ROLE_GUEST', 'Invitado',
                'Usuario con permisos limitados o temporales', now()),
               (uuid_generate_v4(), v_tenant_id, 'ROLE_SUPPORT', 'Soporte',
                'Encargado de brindar asistencia técnica o funcional', now())
        ON CONFLICT (tenant_id, code) DO NOTHING;
    END
$$;
