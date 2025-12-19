-- =========================================
-- 2. Asignar plan STARTER a todos los tenants
-- =========================================
UPDATE core.tenants t
SET plan_id = p.id
    FROM core.plans p
WHERE p.tenant_id = t.id
  AND p.name = 'STARTER';

-- =========================================
-- 3. Validación: ningún tenant sin plan
-- =========================================
DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM core.tenants
        WHERE plan_id IS NULL
    ) THEN
        RAISE EXCEPTION 'Hay tenants sin plan asignado';
END IF;
END $$;

-- =========================================
-- 4. Asegurar NOT NULL
-- =========================================
ALTER TABLE core.tenants
    ALTER COLUMN plan_id SET NOT NULL;

-- =========================================
-- 5. Agregar FK
-- =========================================
ALTER TABLE core.tenants
    ADD CONSTRAINT fk_tenant_plan
        FOREIGN KEY (plan_id)
            REFERENCES core.plans(id);

-- =========================================
-- 6. Eliminar columna antigua
-- =========================================
ALTER TABLE core.tenants
    DROP COLUMN plan;
