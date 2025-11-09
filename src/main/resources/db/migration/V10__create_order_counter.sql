CREATE TABLE ordering.order_counters
(
    tenant_id uuid PRIMARY KEY REFERENCES core.tenants(id) ON DELETE CASCADE,
    current_year int NOT NULL,
    last_number  int NOT NULL DEFAULT 0
);