-- USERS
ALTER TABLE iam.users
    ADD COLUMN status text;

UPDATE iam.users
SET status = CASE
                 WHEN is_active = true THEN 'ACTIVE'
                 ELSE 'INACTIVE'
    END;

ALTER TABLE iam.users
    ALTER COLUMN status SET NOT NULL;

ALTER TABLE iam.users
    DROP COLUMN is_active;

-- PRICE LISTS
ALTER TABLE pricing.price_lists
    ADD COLUMN status text;

UPDATE pricing.price_lists
SET status = CASE
                 WHEN is_active = true THEN 'ACTIVE'
                 ELSE 'INACTIVE'
    END;

ALTER TABLE pricing.price_lists
    ALTER COLUMN status SET NOT NULL;

ALTER TABLE pricing.price_lists
    DROP COLUMN is_active;

-- PRODUCTS
ALTER TABLE catalog.products
    ADD COLUMN status text;

UPDATE catalog.products
SET status = CASE
                 WHEN is_active = true THEN 'ACTIVE'
                 ELSE 'INACTIVE'
    END;

ALTER TABLE catalog.products
    ALTER COLUMN status SET NOT NULL;

ALTER TABLE catalog.products
    DROP COLUMN is_active;

-- PRODUCT VARIANTS
ALTER TABLE catalog.product_variants
    ADD COLUMN status text;

UPDATE catalog.product_variants
SET status = CASE
                 WHEN is_active = true THEN 'ACTIVE'
                 ELSE 'INACTIVE'
    END;

ALTER TABLE catalog.product_variants
    ALTER COLUMN status SET NOT NULL;

ALTER TABLE catalog.product_variants
    DROP COLUMN is_active;

CREATE INDEX idx_users_status ON iam.users (status);
CREATE INDEX idx_price_lists_status ON pricing.price_lists (status);
CREATE INDEX idx_products_status ON catalog.products (status);
CREATE INDEX idx_product_variants_status ON catalog.product_variants (status);



