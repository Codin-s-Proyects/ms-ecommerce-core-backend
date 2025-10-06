CREATE SCHEMA IF NOT EXISTS core;
CREATE SCHEMA IF NOT EXISTS iam;
CREATE SCHEMA IF NOT EXISTS catalog;
CREATE SCHEMA IF NOT EXISTS catalog_attrs;
CREATE SCHEMA IF NOT EXISTS templates;
CREATE SCHEMA IF NOT EXISTS search;
CREATE SCHEMA IF NOT EXISTS inventory;
CREATE SCHEMA IF NOT EXISTS pricing;
CREATE SCHEMA IF NOT EXISTS orders;
CREATE SCHEMA IF NOT EXISTS payments;
CREATE SCHEMA IF NOT EXISTS shipping;
CREATE SCHEMA IF NOT EXISTS compliance_pe;
CREATE SCHEMA IF NOT EXISTS content;
CREATE SCHEMA IF NOT EXISTS integration;
CREATE SCHEMA IF NOT EXISTS admin;

-- ======================
-- CORE: tenants
-- ======================
CREATE TABLE core.tenants
(
    id         uuid PRIMARY KEY       DEFAULT uuid_generate_v4(),
    slug       citext UNIQUE NOT NULL,
    name       text          NOT NULL,
    plan       text          NOT NULL DEFAULT 'free',
    settings   jsonb         NOT NULL DEFAULT '{}'::jsonb,
    created_at timestamptz   NOT NULL DEFAULT now()
);

-- ======================
-- IAM SCHEMA
-- ======================
CREATE TABLE iam.users
(
    id          uuid PRIMARY KEY     DEFAULT uuid_generate_v4(),
    tenant_id   uuid        NOT NULL REFERENCES core.tenants (id) ON DELETE CASCADE,
    user_type   text        NOT NULL DEFAULT 'client', -- admin|client|staff|supplier...
    is_active   boolean     NOT NULL DEFAULT true,
    mfa_enabled boolean     NOT NULL DEFAULT false,
    mfa_secret  bytea,
    last_login  timestamptz,
    meta        jsonb       NOT NULL DEFAULT '{}'::jsonb,
    created_at  timestamptz NOT NULL DEFAULT now()
);

CREATE TABLE iam.credentials
(
    id          uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id     uuid    NOT NULL REFERENCES iam.users (id) ON DELETE CASCADE,
    type        text    NOT NULL, -- 'email' | 'username' | 'phone' | 'oauth'
    identifier  citext  NOT NULL, -- ej: correo o username
    secret_hash text,             -- Argon2id hash si aplica
    is_primary  boolean NOT NULL DEFAULT false,
    UNIQUE (type, identifier)
);

CREATE TABLE iam.roles
(
    id          uuid PRIMARY KEY     DEFAULT uuid_generate_v4(),
    tenant_id   uuid        NOT NULL REFERENCES core.tenants (id) ON DELETE CASCADE,
    code        text        NOT NULL, -- admin|manager|buyer|support
    name        text        NOT NULL,
    description text,
    created_at  timestamptz NOT NULL DEFAULT now(),
    UNIQUE (tenant_id, code)
);

CREATE TABLE iam.user_roles
(
    id          uuid PRIMARY KEY     DEFAULT uuid_generate_v4(),
    user_id     uuid        NOT NULL REFERENCES iam.users (id) ON DELETE CASCADE,
    role_id     uuid        NOT NULL REFERENCES iam.roles (id) ON DELETE CASCADE,
    assigned_by uuid REFERENCES iam.users (id),
    assigned_at timestamptz NOT NULL DEFAULT now()
);

CREATE TABLE iam.sessions
(
    id          uuid PRIMARY KEY     DEFAULT uuid_generate_v4(),
    tenant_id   uuid        NOT NULL REFERENCES core.tenants (id) ON DELETE CASCADE,
    user_id     uuid        NOT NULL REFERENCES iam.users (id) ON DELETE CASCADE,
    device_info text,
    ip          inet,
    created_at  timestamptz NOT NULL DEFAULT now(),
    last_seen   timestamptz NOT NULL DEFAULT now(),
    revoked     boolean     NOT NULL DEFAULT false
);

CREATE TABLE iam.refresh_tokens
(
    id          uuid PRIMARY KEY     DEFAULT uuid_generate_v4(),
    tenant_id   uuid        NOT NULL REFERENCES core.tenants (id) ON DELETE CASCADE,
    user_id     uuid        NOT NULL REFERENCES iam.users (id) ON DELETE CASCADE,
    token_hash  text        NOT NULL,
    issued_at   timestamptz NOT NULL DEFAULT now(),
    expires_at  timestamptz,
    revoked     boolean     NOT NULL DEFAULT false,
    device_info text,
    UNIQUE (token_hash)
);

CREATE TABLE iam.oauth_clients
(
    id                 uuid PRIMARY KEY     DEFAULT uuid_generate_v4(),
    tenant_id          uuid        NOT NULL REFERENCES core.tenants (id) ON DELETE CASCADE,
    client_id          text        NOT NULL,
    client_secret_hash text,
    name               text,
    redirect_uris      text[],
    grant_types        text[],
    scopes             text[],
    is_confidential    boolean     NOT NULL DEFAULT true,
    created_at         timestamptz NOT NULL DEFAULT now(),
    UNIQUE (tenant_id, client_id)
);

CREATE TABLE iam.audit_log
(
    id             uuid PRIMARY KEY     DEFAULT uuid_generate_v4(),
    tenant_id      uuid        NOT NULL REFERENCES core.tenants (id) ON DELETE CASCADE,
    actor_user_id  uuid        REFERENCES iam.users (id) ON DELETE SET NULL,
    action         text        NOT NULL,
    target_user_id uuid        REFERENCES iam.users (id) ON DELETE SET NULL,
    metadata       jsonb       NOT NULL DEFAULT '{}'::jsonb,
    created_at     timestamptz NOT NULL DEFAULT now()
);

-- ======================
-- RLS en IAM
-- ======================
ALTER TABLE iam.users
    ENABLE ROW LEVEL SECURITY;
CREATE POLICY p_users_tenant ON iam.users
    USING (tenant_id = current_setting('app.tenant_id')::uuid);

ALTER TABLE iam.sessions
    ENABLE ROW LEVEL SECURITY;
CREATE POLICY p_sessions_tenant ON iam.sessions
    USING (tenant_id = current_setting('app.tenant_id')::uuid);

ALTER TABLE iam.refresh_tokens
    ENABLE ROW LEVEL SECURITY;
CREATE POLICY p_tokens_tenant ON iam.refresh_tokens
    USING (tenant_id = current_setting('app.tenant_id')::uuid);