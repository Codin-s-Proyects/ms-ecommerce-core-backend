-- =========================================
-- USER PROFILES
-- =========================================

CREATE TABLE iam.user_profiles
(
    id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),

    user_id uuid NOT NULL UNIQUE
        REFERENCES iam.users(id) ON DELETE CASCADE,

    tenant_id uuid NOT NULL
        REFERENCES core.tenants(id) ON DELETE CASCADE,

    first_name text NOT NULL,
    last_name  text NOT NULL,
    email      text NOT NULL,
    phone      text NOT NULL,

    document_type   text,
    document_number text,

    created_at timestamptz NOT NULL DEFAULT now(),
    updated_at timestamptz NOT NULL DEFAULT now()
);


CREATE INDEX idx_user_profiles_tenant
    ON iam.user_profiles (tenant_id);

CREATE INDEX idx_user_profiles_document
    ON iam.user_profiles (document_type, document_number);

CREATE UNIQUE INDEX uq_user_document
    ON iam.user_profiles (tenant_id, document_type, document_number)
    WHERE document_number IS NOT NULL;