ALTER TABLE iam.sessions
    ADD COLUMN device_id text;

UPDATE iam.sessions
SET device_id = 'legacy-' || id::text
WHERE device_id IS NULL;

ALTER TABLE iam.sessions
    RENAME COLUMN device_info TO device_info_text;

ALTER TABLE iam.sessions
    ADD COLUMN device_info jsonb NOT NULL DEFAULT '{}'::jsonb;

UPDATE iam.sessions
SET device_info = jsonb_build_object(
        'raw', device_info_text
                  )
WHERE device_info_text IS NOT NULL;

ALTER TABLE iam.sessions
    DROP COLUMN device_info_text;

CREATE UNIQUE INDEX uq_sessions_user_device_active
    ON iam.sessions (user_id, device_id)
    WHERE revoked = false;

-- Verifica sesiones activas duplicadas por usuario + dispositivo
SELECT user_id, device_id, COUNT(*)
FROM iam.sessions
WHERE revoked = false
GROUP BY user_id, device_id
HAVING COUNT(*) > 1;

