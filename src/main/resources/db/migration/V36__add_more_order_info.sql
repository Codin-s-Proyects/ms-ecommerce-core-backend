ALTER TABLE ordering.orders
    ALTER COLUMN user_id DROP NOT NULL;

ALTER TABLE ordering.orders
    ADD COLUMN tracking_token uuid NOT NULL DEFAULT gen_random_uuid(),
    ADD CONSTRAINT uq_orders_tracking UNIQUE (tracking_token);

CREATE TABLE ordering.order_customers
(
    id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    order_id uuid NOT NULL UNIQUE
        REFERENCES ordering.orders(id) ON DELETE CASCADE,
    first_name      text NOT NULL,
    last_name       text NOT NULL,
    email           text NOT NULL,
    phone           text NOT NULL,
    document_type   text NOT NULL,
    document_number text NOT NULL,
    created_at timestamptz DEFAULT now() NOT NULL
);

CREATE TABLE ordering.order_shipping_addresses
(
    id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    order_id uuid NOT NULL UNIQUE
        REFERENCES ordering.orders(id) ON DELETE CASCADE,
    department   text NOT NULL,
    province     text NOT NULL,
    district     text NOT NULL,
    address_line text NOT NULL,
    reference    text,
    latitude     numeric(10, 7),
    longitude    numeric(10, 7),
    created_at timestamptz DEFAULT now() NOT NULL
);
