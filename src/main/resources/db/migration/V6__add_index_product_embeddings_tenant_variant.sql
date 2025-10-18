CREATE UNIQUE INDEX IF NOT EXISTS uq_product_embeddings_tenant_variant
    ON search.product_embeddings (tenant_id, product_variant_id);
