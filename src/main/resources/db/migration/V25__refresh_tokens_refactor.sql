-- =========================================
-- 1. Agregar session_id (nullable temporal)
-- =========================================
ALTER TABLE iam.refresh_tokens
    ADD COLUMN session_id uuid;

-- =========================================
-- 2. Backfill: asociar refresh_tokens a sesiones
-- =========================================
UPDATE iam.refresh_tokens rt
SET session_id = s.id
    FROM iam.sessions s
WHERE s.user_id = rt.user_id
  AND s.tenant_id = rt.tenant_id
  AND s.revoked = false
  AND rt.session_id IS NULL;

-- =========================================
-- 3. Validación: todos los refresh tokens deben tener sesión
-- =========================================
DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM iam.refresh_tokens
        WHERE session_id IS NULL
    ) THEN
        RAISE EXCEPTION 'Existen refresh_tokens sin session asociada';
END IF;
END $$;

-- =========================================
-- 4. Hacer NOT NULL + FK
-- =========================================
ALTER TABLE iam.refresh_tokens
    ALTER COLUMN session_id SET NOT NULL;

ALTER TABLE iam.refresh_tokens
    ADD CONSTRAINT fk_refresh_token_session
        FOREIGN KEY (session_id)
            REFERENCES iam.sessions(id)
            ON DELETE CASCADE;

-- =========================================
-- 5. Eliminar columnas redundantes
-- =========================================
ALTER TABLE iam.refresh_tokens
    DROP COLUMN tenant_id CASCADE,
    DROP COLUMN user_id CASCADE,
    DROP COLUMN device_info;
