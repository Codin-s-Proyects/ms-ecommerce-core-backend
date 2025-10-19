-- =========================================
-- CATEGORIES
-- =========================================
CREATE TABLE catalog.categories
(
    id          uuid PRIMARY KEY     DEFAULT uuid_generate_v4(),
    tenant_id   uuid        NOT NULL REFERENCES core.tenants (id) ON DELETE CASCADE,
    parent_id   uuid REFERENCES catalog.categories (id),
    name        text        NOT NULL,
    slug        citext      NOT NULL,
    description text,
    created_at  timestamptz NOT NULL DEFAULT now(),
    updated_at  timestamptz NOT NULL DEFAULT now(),
    UNIQUE (tenant_id, slug)
);

-- =========================================
-- BRANDS
-- =========================================
CREATE TABLE catalog.brands
(
    id          uuid PRIMARY KEY     DEFAULT uuid_generate_v4(),
    tenant_id   uuid        NOT NULL REFERENCES core.tenants (id) ON DELETE CASCADE,
    name        text        NOT NULL,
    slug        citext      NOT NULL,
    description text,
    logo_url    text,
    created_at  timestamptz NOT NULL DEFAULT now(),
    UNIQUE (tenant_id, slug)
);

-- =========================================
-- PRODUCTS
-- =========================================
CREATE TABLE catalog.products
(
    id           uuid PRIMARY KEY     DEFAULT uuid_generate_v4(),
    tenant_id    uuid        NOT NULL REFERENCES core.tenants (id) ON DELETE CASCADE,
    category_id  uuid REFERENCES catalog.categories (id),
    brand_id     uuid REFERENCES catalog.brands (id),
    name         text        NOT NULL,
    slug         citext      NOT NULL,
    description  text,
    is_active    boolean     NOT NULL DEFAULT true,
    has_variants boolean     NOT NULL DEFAULT false,
    meta         jsonb       NOT NULL DEFAULT '{}'::jsonb, -- información extra, SEO, etc.
    created_at   timestamptz NOT NULL DEFAULT now(),
    updated_at   timestamptz NOT NULL DEFAULT now(),
    UNIQUE (tenant_id, slug)
);

-- =========================================
-- ATTRIBUTES (definición general)
-- =========================================
CREATE TABLE catalog.attributes
(
    id         uuid PRIMARY KEY     DEFAULT uuid_generate_v4(),
    tenant_id  uuid        NOT NULL REFERENCES core.tenants (id) ON DELETE CASCADE,
    code       text        NOT NULL, -- Ej: color, talla, ram
    name       text        NOT NULL, -- Ej: Color, Talla, RAM
    data_type  text        NOT NULL, -- string | number | boolean | list
    created_at timestamptz NOT NULL DEFAULT now(),
    UNIQUE (tenant_id, code)
);

-- =========================================
-- ATTRIBUTE VALUES
-- =========================================
CREATE TABLE catalog.attribute_values
(
    id           uuid PRIMARY KEY     DEFAULT uuid_generate_v4(),
    attribute_id uuid        NOT NULL REFERENCES catalog.attributes (id) ON DELETE CASCADE,
    value        text        NOT NULL, -- Ej: 'rojo', 'M', 'SSD'
    label        text,
    created_at   timestamptz NOT NULL DEFAULT now(),
    UNIQUE (attribute_id, value)
);

-- =========================================
-- CATEGORY ↔ ATTRIBUTE mapping
-- =========================================
CREATE TABLE catalog.category_attributes
(
    id                   uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    tenant_id            uuid    NOT NULL REFERENCES core.tenants (id) ON DELETE CASCADE,
    category_id          uuid    NOT NULL REFERENCES catalog.categories (id) ON DELETE CASCADE,
    attribute_id         uuid    NOT NULL REFERENCES catalog.attributes (id) ON DELETE CASCADE,
    is_variant_attribute boolean NOT NULL DEFAULT true,
    UNIQUE (category_id, attribute_id)
);

-- =========================================
-- PRODUCTS ↔ ATTRIBUTES (opcional, metadatos fijos)
-- =========================================
CREATE TABLE catalog.product_attributes
(
    id           uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    tenant_id    uuid NOT NULL REFERENCES core.tenants (id) ON DELETE CASCADE,
    product_id   uuid NOT NULL REFERENCES catalog.products (id) ON DELETE CASCADE,
    attribute_id uuid NOT NULL REFERENCES catalog.attributes (id) ON DELETE CASCADE,
    value        text,
    UNIQUE (product_id, attribute_id)
);

-- =========================================
-- PRODUCT VARIANTS
-- =========================================
CREATE TABLE catalog.product_variants
(
    id         uuid PRIMARY KEY     DEFAULT uuid_generate_v4(),
    tenant_id  uuid        NOT NULL REFERENCES core.tenants (id) ON DELETE CASCADE,
    product_id uuid        NOT NULL REFERENCES catalog.products (id) ON DELETE CASCADE,
    sku        text UNIQUE NOT NULL,
    name       text        NOT NULL,
    attributes jsonb       NOT NULL DEFAULT '{}'::jsonb, -- { "color":"rojo", "talla":"M" }
    image_url  text,
    is_active  boolean     NOT NULL DEFAULT true,
    created_at timestamptz NOT NULL DEFAULT now(),
    updated_at timestamptz NOT NULL DEFAULT now()
);

-- =========================================
-- SEARCH VECTOR INDEX (pgvector)
-- =========================================
CREATE TABLE search.product_embeddings
(
    id                 uuid PRIMARY KEY      DEFAULT uuid_generate_v4(),
    tenant_id          uuid         NOT NULL REFERENCES core.tenants (id) ON DELETE CASCADE,
    product_variant_id uuid         NOT NULL REFERENCES catalog.product_variants (id) ON DELETE CASCADE,
    vector             vector(1536) NOT NULL,                     -- dimensión de embedding, ajustable según modelo
    metadata           jsonb        NOT NULL DEFAULT '{}'::jsonb, -- info del producto usada en embeddings
    created_at         timestamptz  NOT NULL DEFAULT now()
);

-- =========================================
-- INDEXES
-- =========================================
CREATE INDEX idx_categories_tenant ON catalog.categories (tenant_id);
CREATE INDEX idx_products_tenant ON catalog.products (tenant_id);
CREATE INDEX idx_variants_tenant ON catalog.product_variants (tenant_id);
CREATE INDEX idx_embeddings_tenant ON search.product_embeddings (tenant_id);
CREATE INDEX idx_embeddings_vector_cosine
    ON search.product_embeddings USING ivfflat (vector vector_cosine_ops)
    WITH (lists = 100);

-- =========================================
-- TRIGGERS
-- =========================================
CREATE OR REPLACE FUNCTION set_updated_at()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at = now();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_products_updated
    BEFORE UPDATE
    ON catalog.products
    FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_variants_updated
    BEFORE UPDATE
    ON catalog.product_variants
    FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- =========================================
-- RLS POLICIES
-- =========================================
ALTER TABLE catalog.categories
    ENABLE ROW LEVEL SECURITY;
CREATE POLICY p_categories_tenant ON catalog.categories
    USING (tenant_id = current_setting('app.tenant_id')::uuid);

ALTER TABLE catalog.attributes
    ENABLE ROW LEVEL SECURITY;
CREATE POLICY p_attributes_tenant ON catalog.attributes
    USING (tenant_id = current_setting('app.tenant_id')::uuid);

ALTER TABLE catalog.attribute_values
    ENABLE ROW LEVEL SECURITY;
CREATE POLICY p_attribute_values_tenant ON catalog.attribute_values
    USING (
    attribute_id IN (SELECT id
                     FROM catalog.attributes
                     WHERE tenant_id = current_setting('app.tenant_id')::uuid)
    );

ALTER TABLE catalog.category_attributes
    ENABLE ROW LEVEL SECURITY;
CREATE POLICY p_category_attributes_tenant ON catalog.category_attributes
    USING (tenant_id = current_setting('app.tenant_id')::uuid);

ALTER TABLE catalog.product_attributes
    ENABLE ROW LEVEL SECURITY;
CREATE POLICY p_product_attributes_tenant ON catalog.product_attributes
    USING (tenant_id = current_setting('app.tenant_id')::uuid);

ALTER TABLE catalog.products
    ENABLE ROW LEVEL SECURITY;
CREATE POLICY p_products_tenant ON catalog.products
    USING (tenant_id = current_setting('app.tenant_id')::uuid);

ALTER TABLE catalog.product_variants
    ENABLE ROW LEVEL SECURITY;
CREATE POLICY p_product_variants_tenant ON catalog.product_variants
    USING (tenant_id = current_setting('app.tenant_id')::uuid);

ALTER TABLE search.product_embeddings
    ENABLE ROW LEVEL SECURITY;
CREATE POLICY p_embeddings_tenant ON search.product_embeddings
    USING (tenant_id = current_setting('app.tenant_id')::uuid);
