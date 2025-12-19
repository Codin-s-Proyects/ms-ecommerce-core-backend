-- =========================================
-- TABLE: plans
-- =========================================
CREATE TABLE IF NOT EXISTS core.plans
(
    id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),

    tenant_id uuid NOT NULL REFERENCES core.tenants(id) ON DELETE CASCADE,

    name text NOT NULL,
    description text,

    commission_rate numeric(5,2) NOT NULL, -- porcentaje
    monthly_fee numeric(12,2) NOT NULL,    -- mensualidad
    onboarding_fee numeric(12,2) NOT NULL, -- onboarding

    gmv_min numeric(12,2),
    gmv_max numeric(12,2),

    status text NOT NULL,
    created_at timestamptz NOT NULL DEFAULT now(),

    CONSTRAINT uq_tenant_plan UNIQUE (tenant_id, name)
);

CREATE INDEX idx_plans_tenant
    ON core.plans (tenant_id);

-- =========================================
-- SEED DATA: PLANES POR TENANT
-- =========================================
INSERT INTO core.plans (
    tenant_id,
    name,
    description,
    commission_rate,
    monthly_fee,
    onboarding_fee,
    gmv_min,
    gmv_max,
    status
)
SELECT
    t.id,
    p.name,
    p.description,
    p.commission_rate,
    p.monthly_fee,
    p.onboarding_fee,
    p.gmv_min,
    p.gmv_max,
    'ACTIVE'
FROM core.tenants t
         CROSS JOIN (
    VALUES
        (
            'STARTER',
            'Emprendedor pequeño (0-50 ventas/mes)',
            3.50,
            0.00,
            0.00,
            0.00,
            5000.00
        ),
        (
            'PROFESSIONAL',
            'Negocio en crecimiento (50-300 ventas/mes)',
            2.00,
            120.00,
            250.00,
            5000.00,
            30000.00
        ),
        (
            'ENTERPRISE',
            'Negocio establecido (300+ ventas/mes)',
            1.50,
            500.00,
            600.00,
            30000.00,
            NULL
        )
) AS p (
        name,
        description,
        commission_rate,
        monthly_fee,
        onboarding_fee,
        gmv_min,
        gmv_max
    );
