ALTER TABLE ordering.order_items
    ADD COLUMN product_id uuid REFERENCES catalog.products(id);

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM catalog.products) THEN
        RAISE EXCEPTION 'Cannot backfill ordering.order_items.product_id because catalog.products is empty';
    END IF;
END $$;

UPDATE ordering.order_items oi
SET product_id = random_product.id
FROM LATERAL (
    SELECT id
    FROM catalog.products
    ORDER BY random()
    LIMIT 1
) random_product
WHERE oi.product_id IS NULL;

ALTER TABLE ordering.order_items
    ALTER COLUMN product_id SET NOT NULL;
